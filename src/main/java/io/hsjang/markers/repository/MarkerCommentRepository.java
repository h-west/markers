package io.hsjang.markers.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.MarkerComment;
import reactor.core.publisher.Flux;

public interface MarkerCommentRepository extends ReactiveMongoRepository<MarkerComment, String>{

	Flux<MarkerComment> findByTypeAndTargetIdOrderByIdDesc(String type, String targetId, Pageable page);
}
