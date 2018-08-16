package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class MarkerComment {
	
	@Id
	String id;
	
	@Indexed
	String type;
	@Indexed
	String targetId;
	
	String contents;
	
	User user;
	
	public MarkerComment addTypeTargetId(String type, String targetId) {
		this.type = type;
		this.targetId = targetId;
		return this;
	}
	
	public MarkerComment addUser(User user) {
		this.user = user;
		return this;
	}
}
