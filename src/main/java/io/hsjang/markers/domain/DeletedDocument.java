package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class DeletedDocument {

	@Id
	String id;
	String collection;
	Object document;
	
	public DeletedDocument(String collection, Object document) {
		setCollection(collection);
		setDocument(document);
	}
}
