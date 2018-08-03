package io.hsjang.markers.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class User {

	@Id
	String id;
	String userId;
	String name;
	String image;
	List<String> auths;
	
	/*public User(UserProvider provider) {
		this.userId = provider.getUserId();
	}*/
}
