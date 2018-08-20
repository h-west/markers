package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class MarkerShop {
	
	@Id
	String id;
	
	@Indexed
	String markerId;
	String name;
	int vCount = 0;

	User user;
	
	public MarkerShop addMarkerId(String markerId) {
		setMarkerId(markerId);
		return this;
	}
	
	public MarkerShop addName(String name) {
		setName(name);
		return this;
	}
	
	public MarkerShop addUser(User user) {
		setUser(user);
		return this;
	}
	
}
