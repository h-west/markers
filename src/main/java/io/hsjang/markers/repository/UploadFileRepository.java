package io.hsjang.markers.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.UploadFile;

public interface UploadFileRepository extends ReactiveMongoRepository<UploadFile, String>{

}
