package io.hsjang.markers.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.hsjang.markers.domain.DeletedDocument;

public interface DeletedDocumentRepository extends ReactiveMongoRepository<DeletedDocument, String>{

}
