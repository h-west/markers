package io.hsjang.markers.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.MarkerDetail;

public interface MarkerDetailRepository extends ReactiveMongoRepository<MarkerDetail, String>{

}
