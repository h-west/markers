package io.hsjang.markers.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hsjang.markers.common.model.Page;
import io.hsjang.markers.domain.MarkerComment;
import io.hsjang.markers.domain.User;
import io.hsjang.markers.repository.MarkerCommentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/marker")
public class MarkerCommentController {
	
	@Autowired
	MarkerCommentRepository markerCommentRepository;

	@GetMapping("/{type}/comment/{targetId}")
	public Flux<MarkerComment> comments(@PathVariable String type, @PathVariable String targetId, Page page) {
		return markerCommentRepository.findByTypeAndTargetIdOrderByIdDesc(type, targetId, PageRequest.of(page.getPage(), page.getSize()) );
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/{type}/comment/{targetId}")
	public Mono<MarkerComment> comment(@PathVariable String type, @PathVariable String targetId, MarkerComment comment, @AuthenticationPrincipal User user) {
		return markerCommentRepository.save(comment.addTypeTargetId(type,targetId).addUser(user));
	}
	
}
