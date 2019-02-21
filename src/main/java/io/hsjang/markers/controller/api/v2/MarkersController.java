package io.hsjang.markers.controller.api.v2;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hsjang.markers.domain.Marker;
import io.hsjang.markers.domain.MarkerDetail;
import io.hsjang.markers.domain.User;
import io.hsjang.markers.repository.MarkerDetailRepository;
import io.hsjang.markers.repository.MarkerRepository;
import io.hsjang.markers.repository.UserRepository;
import io.hsjang.markers.service.map.MapService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/markers")
public class MarkersController {
	
	@Autowired
	MarkerRepository markerRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MarkerDetailRepository markerDetailRepository;

	
	/************************
	 * GET ITEM
	 */
	@GetMapping("/{id}")
	public Mono<MarkerDetail> marker(@PathVariable String id) {
		
		return markerDetailRepository.findById(id)
				.flatMap(md->userRepository.findById(md.getUserId()).map(md::addUser));
	}
	
	
	/************************
	 * POST ITEM 
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/point/{lat}/{lng}")
	public Mono<Marker<GeoJsonPoint>> point(@RequestBody MarkerDetail markerDetail, @PathVariable double lat, @PathVariable double lng, @AuthenticationPrincipal User user) {
		return markerRepository.save(new Marker<GeoJsonPoint>(new GeoJsonPoint(lat, lng),markerDetail))
				.flatMap(m ->
					markerDetailRepository.save(
							markerDetail
								.addMarkerId(m.getId())
								.addUserId(user.getUserId())
							).map(md -> m)
				);
	}
	
	
	/** 참고 **/
	/*
	
	@GetMapping("/")
	public String index(Model model,
						@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
						@AuthenticationPrincipal OAuth2User oauth2User) {
		model.addAttribute("userName", oauth2User.getName());
		model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
		model.addAttribute("userAttributes", oauth2User.getAttributes());
		return "index";
	}
	
	public Mono<Map<String, String>> hello(Mono<Principal> principal) {
		return principal
			.map(Principal::getName)
			.map(this::helloMessage);
	}*/
}
