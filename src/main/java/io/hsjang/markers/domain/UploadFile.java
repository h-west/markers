package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpHeaders;

import lombok.Data;

@Document
@Data
public class UploadFile {
	
	@Id
	String id;
	
	String originFileName;
	HttpHeaders headers;
	String savefileName;
	String url;
	
	String userId;
	
	public UploadFile() {}
	public UploadFile(String originFileName
			,HttpHeaders headers
			,String savefileName
			,String url
			,String userId) {
		setOriginFileName(originFileName);
		setHeaders(headers);
		setSavefileName(savefileName);
		setUrl(url);
		setUserId(userId);
	}
	
	public UploadFile addUserId(String userId) {
		setUserId(userId);
		return this;
	}
}
