package io.hsjang.markers.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.hsjang.markers.common.type.MarkerType;
import lombok.Data;

@Document
@JsonInclude(Include.NON_NULL)
@Data
public class MarkerDetail {
	
	@Id
	String markerId;
	String type;
	String title;
	String image = "/upload-image/default.png";
	Map<String,Object> cts; 

	String userId;
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
