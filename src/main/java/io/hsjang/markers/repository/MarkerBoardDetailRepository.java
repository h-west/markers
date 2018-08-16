package io.hsjang.markers.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.MarkerBoardDetail;

public interface MarkerBoardDetailRepository extends ReactiveMongoRepository<MarkerBoardDetail, String>{

}
