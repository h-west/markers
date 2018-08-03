package io.hsjang.markers.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User, String>{

}
