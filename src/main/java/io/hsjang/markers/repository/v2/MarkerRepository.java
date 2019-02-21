package io.hsjang.markers.repository.v2;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.v2.Marker;
import reactor.core.publisher.Flux;

public interface MarkerRepository extends ReactiveMongoRepository<Marker<?>, String>{

	Flux<Marker<?>> findByGeometryNearOrderByIdDesc(Point point , Distance d);
}
