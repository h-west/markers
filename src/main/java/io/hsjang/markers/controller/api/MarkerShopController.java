package io.hsjang.markers.controller.api;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hsjang.markers.common.model.Page;
import io.hsjang.markers.domain.DeletedDocument;
import io.hsjang.markers.domain.MarkerBoard;
import io.hsjang.markers.domain.MarkerBoardDetail;
import io.hsjang.markers.domain.User;
import io.hsjang.markers.repository.DeletedDocumentRepository;
import io.hsjang.markers.repository.MarkerBoardDetailRepository;
import io.hsjang.markers.repository.MarkerBoardRepository;
import io.hsjang.markers.repository.MarkerCommentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/marker")
public class MarkerShopController {
	
	@Autowired
	MarkerBoardRepository markerBoardRepository;

	@Autowired
	MarkerBoardDetailRepository markerBoardDetailRepository;

	@Autowired
	MarkerCommentRepository markerCommentRepository;

	@Autowired
	DeletedDocumentRepository deletedDocumentRepository;
	
	@GetMapping("/{markerId}/shop")
	public Flux<MarkerBoard> boards(@PathVariable String markerId, Page page) {
		return markerBoardRepository.findByMarkerIdOrderByIdDesc(markerId, PageRequest.of(page.getPage(), page.getSize()));
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/{markerId}/shop")
	public Mono<MarkerBoard> board(@PathVariable String markerId, @RequestBody MarkerBoardDetail detail, @AuthenticationPrincipal User user) {
		return markerBoardRepository.save(new MarkerBoard().addTitle(detail.getTitle()).addMarkerId(markerId).addUser(user)).flatMap(b->markerBoardDetailRepository.save(detail.addBoardId(b.getId())).map(d->b));
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/shop")
	public Mono<MarkerBoardDetail> uploadBoard(@RequestBody MarkerBoardDetail detail, @AuthenticationPrincipal User user) {
		return markerBoardRepository.findById(detail.getBoardId())
				.filter(b->b.getUser().getUserId().equals(user.getUserId()))
				.flatMap(b->markerBoardRepository.save(b.addTitle(detail.getTitle())))
				.flatMap(b->markerBoardDetailRepository.save(detail));
	}
	
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/shop")
	public Mono<DeletedDocument> deleteBoard(@RequestBody MarkerBoardDetail detail, @AuthenticationPrincipal User user) {
		return markerBoardRepository.findById(detail.getBoardId())
					.filter(b->b.getUser().getUserId().equals(user.getUserId()))
					.flatMap(b->markerBoardRepository.delete(b).then(Mono.just(new DeletedDocument(b))))
					.flatMap(deletedDocumentRepository::save);
		// 나중에 상세정보도 삭제하자.
	}
	
	@GetMapping("/shop/{boardId}")
	public Mono<MarkerBoardDetail> board(@PathVariable String boardId) {
		Page page = new Page(); // default page
		return markerBoardDetailRepository
					.findById(boardId)
					.flatMap(d->
							markerCommentRepository.findByTypeAndTargetIdOrderByIdDesc("board", d.getBoardId(), PageRequest.of(page.getPage(), page.getSize()))
							.collect(Collectors.toList()).map(d::addComments)
					).flatMap(d->markerBoardRepository.findById(d.getBoardId()).map(d::addMarkerBoard));
	}
	

}
