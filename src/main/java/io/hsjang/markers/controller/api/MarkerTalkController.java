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
import io.hsjang.markers.domain.MarkerBoard;
import io.hsjang.markers.domain.MarkerTalk;
import io.hsjang.markers.domain.User;
import io.hsjang.markers.repository.MarkerTalkRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/marker")
public class MarkerTalkController {
	
	@Autowired
	MarkerTalkRepository markerTalkRepository;

	@GetMapping("/{markerId}/talk")
	public Flux<MarkerTalk> talks(@PathVariable String markerId, Page page) {
		return markerTalkRepository.findByMarkerIdOrderByIdDesc(markerId, PageRequest.of(page.getPage(), page.getSize()));
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/{markerId}/talk")
	public Mono<MarkerTalk> talk(@PathVariable String markerId, MarkerBoard board, MarkerTalk talk,  @AuthenticationPrincipal User user) {
		return markerTalkRepository.save(talk.addMarkerId(markerId).addUser(user));
	}
	
}
