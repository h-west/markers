package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Marker<T> {
	
	@Id
	String id;
	String name;
	T geometry;
}
