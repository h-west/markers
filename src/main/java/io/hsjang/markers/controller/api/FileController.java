package io.hsjang.markers.controller.api;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hsjang.markers.domain.UploadFile;
import io.hsjang.markers.domain.User;
import io.hsjang.markers.repository.UploadFileRepository;
import io.hsjang.markers.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/file")
public class FileController implements InitializingBean{
	
	@Value("${path.upload}")
	String uploadFilePath;
	
	@Value("${url.cdn}")
	String cdnUrl;

	@Autowired
	UploadFileRepository uploadFileRepository;

	@PreAuthorize("hasRole('USER')")
	@PostMapping(path="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<UploadFile> upload(@RequestBody Flux<Part> parts, @AuthenticationPrincipal User user) {
		// @formatter:off
		
		return parts
                .filter(part -> part instanceof FilePart) // only retain file parts
                .ofType(FilePart.class).last() // convert the flux to FilePart
                .flatMap(f->saveFile(f,user.getUserId()));
		
		// @formatter:on
	}
	private Mono<UploadFile> saveFile(FilePart filePart, String userId) {
		String originFileName = filePart.filename();
		HttpHeaders headers = filePart.headers();
		
		String ext = originFileName.substring(originFileName.lastIndexOf("."));
		String name = "/" + UUID.randomUUID().toString() + ext;
		String savefileName = uploadFilePath + name;
		filePart.transferTo(new File(savefileName));

		String url = cdnUrl + name;
		return uploadFileRepository.save(new UploadFile(originFileName,headers,savefileName,url,userId));
	}
	private Mono<String> fluxSaveFile(FilePart filePart) {

        // if a file with the same name already exists in a repository, delete and recreate it
//        final String filename = filePart.filename();
//        File file = new File(filename);
//        if (file.exists())
//            file.delete();
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            return Mono.error(e); // if creating a new file fails return an error
//        }
        //filePart.transferTo(new File(UUID.randomUUID().toString()));
        File file = new File("");
        
        try {
            // create an async file channel to store the file on disk
            final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(file.toPath(), StandardOpenOption.WRITE);

            final CloseCondition closeCondition = new CloseCondition();

            // pointer to the end of file offset
            AtomicInteger fileWriteOffset = new AtomicInteger(0);
            // error signal
            AtomicBoolean errorFlag = new AtomicBoolean(false);

            //LOGGER.info("subscribing to file parts");
            // FilePart.content produces a flux of data buffers, each need to be written to the file
            return filePart.content().doOnEach(dataBufferSignal -> {
                if (dataBufferSignal.hasValue() && !errorFlag.get()) {
                    // read data from the incoming data buffer into a file array
                    DataBuffer dataBuffer = dataBufferSignal.get();
                    int count = dataBuffer.readableByteCount();
                    byte[] bytes = new byte[count];
                    dataBuffer.read(bytes);

                    // create a file channel compatible byte buffer
                    final ByteBuffer byteBuffer = ByteBuffer.allocate(count);
                    byteBuffer.put(bytes);
                    byteBuffer.flip();

                    // get the current write offset and increment by the buffer size
                    final int filePartOffset = fileWriteOffset.getAndAdd(count);
                    //LOGGER.info("processing file part at offset {}", filePartOffset);
                    // write the buffer to disk
                    closeCondition.onTaskSubmitted();
                    fileChannel.write(byteBuffer, filePartOffset, null, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            // file part successfuly written to disk, clean up
                            //LOGGER.info("done saving file part {}", filePartOffset);
                            byteBuffer.clear();

                            if (closeCondition.onTaskCompleted())
                                try {
                                    //LOGGER.info("closing after last part");
                                    fileChannel.close();
                                } catch (IOException ignored) {
                                    ignored.printStackTrace();
                                }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            // there as an error while writing to disk, set an error flag
                            errorFlag.set(true);
                            //LOGGER.info("error saving file part {}", filePartOffset);
                        }
                    });
                }
            }).doOnComplete(() -> {
                // all done, close the file channel
                //LOGGER.info("done processing file parts");
                if (closeCondition.canCloseOnComplete())
                    try {
                        //LOGGER.info("closing after complete");
                        fileChannel.close();
                    } catch (IOException ignored) {
                    }

            }).doOnError(t -> {
                // ooops there was an error
                //LOGGER.info("error processing file parts");
                try {
                    fileChannel.close();
                } catch (IOException ignored) {
                }
                // take last, map to a status string
            }).last().map(dataBuffer -> filePart.filename() + " " + (errorFlag.get() ? "error" : "uploaded"));
        } catch (IOException e) {
            // unable to open the file channel, return an error
            //LOGGER.info("error opening the file channel");
            return Mono.error(e);
        }
    }
	@Override
	public void afterPropertiesSet() throws Exception {
		File file = new File(uploadFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

}

class CloseCondition {

    AtomicInteger tasksSubmitted = new AtomicInteger(0);
    AtomicInteger tasksCompleted = new AtomicInteger(0);
    AtomicBoolean allTaskssubmitted = new AtomicBoolean(false);

    /**
     * notify all tasks have been subitted, determine of the file channel can be closed
     * @return true if the asynchronous file stream can be closed
     */
    public boolean canCloseOnComplete() {
        allTaskssubmitted.set(true);
        return tasksCompleted.get() == tasksSubmitted.get();
    }

    /**
     * notify a task has been submitted
     */
    public void onTaskSubmitted() {
        tasksSubmitted.incrementAndGet();
    }

    /**
     * notify a task has been completed
     * @return true if the asynchronous file stream can be closed
     */
    public boolean onTaskCompleted() {
        boolean allSubmittedClosed = tasksSubmitted.get() == tasksCompleted.incrementAndGet();
        return allSubmittedClosed && allTaskssubmitted.get();
    }
}
