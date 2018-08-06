package io.hsjang.markers.service.user.provider.facebook;

import org.springframework.web.reactive.function.client.ClientResponse;

import io.hsjang.markers.service.user.UserInfo;
import reactor.core.publisher.Mono;

@lombok.Data
public class FacebookUserInfo implements UserInfo{

	public static String PREFIX = "F";
	
	String id;
	String name;
	String email;
	Picture Picture;
	
	public static Mono<UserInfo> getUserInfo(ClientResponse response){
		
		if(response.statusCode().isError()) {
			throw new RuntimeException();
		}else {
			return response.bodyToMono(FacebookUserInfo.class);
		}
		
	}

	@Override
	public String getUserId() {
		return PREFIX + this.id;
	}

	@Override
	public String getUserName() {
		return this.name;
	}

	@Override
	public String getUserImage() {
		return Picture.getData().getUrl();
	}

	@Override
	public String getUserEmail() {
		return this.email;
	}
}

class Picture{
	Data data;
	public Data getData() {
		return this.data;
	}
}

class Data{
	String url;
	public String getUrl() {
		return this.url;
	}
}
