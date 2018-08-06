package io.hsjang.markers.domain;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.hsjang.markers.service.user.UserInfo;
import lombok.Data;

@Document
@Data
public class User {

	@Id
	String userId;
	String name;
	String image;
	List<String> auths;
	
	public User(UserInfo userInfo) {
		this.userId = userInfo.getUserId();
		this.name = userInfo.getUserId();
		this.image = userInfo.getUserId();
		auths = Arrays.asList("USER");
	}
}
