package io.hsjang.markers.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.MarkerShop;

public interface MarkerShopRepository extends ReactiveMongoRepository<MarkerShop, String>{

}
