package io.hsjang.markers.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nimbusds.jose.Payload;

import io.hsjang.markers.service.user.UserInfo;
import lombok.Data;
import net.minidev.json.JSONObject;

@Document
@Data
public class User {

	@Id
	String userId;
	String name;
	String email;
	String image;
	String auths;
	
	public User(UserInfo userInfo) {
		this.userId = userInfo.getUserId();
		this.name = userInfo.getUserName();
		this.email = userInfo.getUserEmail();
		this.image = userInfo.getUserImage();
		auths = "USER";
	}
	
	public User(Payload payload) {
		JSONObject jsonUser = payload.toJSONObject();
		this.userId = jsonUser.getAsString("userId");
		this.name = jsonUser.getAsString("name");
		this.email = jsonUser.getAsString("email");
		this.image = jsonUser.getAsString("image");
		this.auths = jsonUser.getAsString("auths");
	}
}
