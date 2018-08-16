package io.hsjang.markers.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.MarkerBoard;
import reactor.core.publisher.Flux;

public interface MarkerBoardRepository extends ReactiveMongoRepository<MarkerBoard, String>{

	Flux<MarkerBoard> findByMarkerIdOrderByIdDesc(String markerId, Pageable page);
}
