package io.hsjang.markers.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hsjang.markers.domain.Marker;
import io.hsjang.markers.repository.MarkerRepository;
import io.hsjang.markers.service.map.MapService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/markers")
public class MarkersController {
	
	@Autowired
	MarkerRepository markerRepository;
	
	@Autowired
	MapService mapService;
	
	@GetMapping("/{zoom}/{lat}/{lng}")
	public Flux<Marker<?>> makers(@PathVariable int zoom, @PathVariable double lat, @PathVariable double lng) {
		return markerRepository.findByGeometryNearOrderByIdDesc(new Point(lat, lng), mapService.getDistanceByZoom(zoom)).take(100);
	}
	
}
