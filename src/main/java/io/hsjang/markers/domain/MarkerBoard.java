package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class MarkerBoard {
	
	@Id
	String id;
	
	@Indexed
	String markerId;
	String title;
	int vCount = 0;

	User user;
	
	public MarkerBoard addMarkerId(String markerId) {
		setMarkerId(markerId);
		return this;
	}
	
	public MarkerBoard addUser(User user) {
		setUser(user);
		return this;
	}
	
}
