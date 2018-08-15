package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.hsjang.markers.type.MarkerType;
import lombok.Data;

@Document
@JsonInclude(Include.NON_NULL)
@Data
public class MarkerDetail {
	
	@Id
	String markerId;
	String type;
	String title;
	String contents; // < map 전환 

	String userId;
	// 작성일 등 메타 >> ID에서 가져올 수있나?
	
	User user;

	public MarkerDetail addMarkerId(String markerId) {
		setMarkerId(markerId);
		return this;
	}
	
	public MarkerDetail addUserId(String userId) {
		setUserId(userId);
		return this;
	}
	
	public MarkerDetail addUser(User user) {
		setUser(user);
		return this;
	}
	
	public String getIcon(){
		return MarkerType.getType(getType()).getIcon();  //없을경우 ? FIXME
	}
	
}
