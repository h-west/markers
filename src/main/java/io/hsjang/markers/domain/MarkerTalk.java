package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class MarkerTalk {
	
	@Id
	String id;
	
	@Indexed
	String markerId;
	String contents;
	
	User user;
	
	public MarkerTalk addMarkerId(String markerId) {
		setMarkerId(markerId);
		return this;
	}
	
	public MarkerTalk addUser(User user) {
		setUser(user);
		return this;
	}
	
}
