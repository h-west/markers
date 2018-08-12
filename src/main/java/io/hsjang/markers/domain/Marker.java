package io.hsjang.markers.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Marker<T> {
	
	@Id
	String id;
	String type = "Feature";
	T geometry;
	Map<String,Object> properties;
	
	public Marker(T geometry, Map<String,Object> properties) {
		setGeometry(geometry);
		setProperties(properties);
	}
}
