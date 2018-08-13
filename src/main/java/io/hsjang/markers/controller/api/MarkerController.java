package io.hsjang.markers.controller.api;

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
import io.hsjang.markers.repository.MarkerDetailRepository;
import io.hsjang.markers.repository.MarkerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class MarkerController {
	
	@Autowired
	MarkerRepository markerRepository;
	
	@Autowired
	MarkerDetailRepository markerDetailRepository;

	/************************
	 * GET ITEMS 
	 */
	@GetMapping("/markers")
	public Flux<Marker<?>> makers() {
		//throw new MarkerException(MarkerExceptionType.UNKONWN);
		return markerRepository.findAll().take(100);
	}
	
	@GetMapping("/markers/{lat}/{lng}")
	public Flux<Marker<?>> makers(@PathVariable double lat, @PathVariable double lng) {
		return markerRepository.findByGeometryNear(new Point(lat, lng), new Distance(10000, Metrics.KILOMETERS));
	}
	
	/************************
	 * GET ITEM
	 */
	@GetMapping("/marker/{id}")
	public Mono<MarkerDetail> maker(@PathVariable String id) {
		return markerDetailRepository.findById(id);
	}
	
	
	/************************
	 * POST ITEM 
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/marker/point/{lat}/{lng}")
	public Mono<Marker<GeoJsonPoint>> point(@RequestBody MarkerDetail markerDetail, @PathVariable double lat, @PathVariable double lng, @AuthenticationPrincipal String userId) {
		return markerRepository.save(new Marker<GeoJsonPoint>(new GeoJsonPoint(lat, lng),markerDetail))
				.map(m ->{
					markerDetailRepository.save(markerDetail.buildMarkerId(m.getId())).subscribe();
					return m;
				});
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
