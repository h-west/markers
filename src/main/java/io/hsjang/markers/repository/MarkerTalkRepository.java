package io.hsjang.markers.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.MarkerTalk;
import reactor.core.publisher.Flux;

public interface MarkerTalkRepository extends ReactiveMongoRepository<MarkerTalk, String>{

	Flux<MarkerTalk> findByMarkerIdOrderByIdDesc(String markerId, Pageable page);
}
