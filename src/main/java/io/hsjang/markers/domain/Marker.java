package io.hsjang.markers.domain;

import java.util.HashMap;
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
	
	public Marker() {}
	
	public Marker(T geometry, Map<String,Object> properties) {
		setGeometry(geometry);
		setProperties(properties);
	}
	
	public Marker(T geometry, MarkerDetail detail) {
		setGeometry(geometry);
		setDetailProperties(detail);
	}
	
	public void setDetailProperties(MarkerDetail detail) {
		Map<String, Object> p = new HashMap<String, Object>();
		
		p.put("title", detail.getTitle());
		p.put("icon", detail.getIcon());
		setProperties(p);
	}
}
