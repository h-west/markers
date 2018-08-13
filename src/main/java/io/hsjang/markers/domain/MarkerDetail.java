package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class MarkerDetail {
	
	@Id
	String markerId;
	String title;
	String icon;
	String userId;
	String contents;
	String url;
	
	public MarkerDetail buildMarkerId(String markerId) {
		setMarkerId(markerId);
		return this;
	}
}
