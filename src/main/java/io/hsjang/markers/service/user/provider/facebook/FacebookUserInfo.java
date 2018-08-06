package io.hsjang.markers.service.user.provider.facebook;

import org.springframework.web.reactive.function.client.ClientResponse;

import io.hsjang.markers.service.user.UserInfo;
import reactor.core.publisher.Mono;

public class FacebookUserInfo implements UserInfo{
	
	String id;
	String name;
	String email;
	Picture Picture;
	
	public static Mono<UserInfo> getUserInfo(ClientResponse response){
		
		if(response.statusCode().isError()) {
			throw new RuntimeException();
		}else {
			//esponse.body(BodyExtractors.toMono(Map.class));
			return response.bodyToMono(FacebookUserInfo.class).map(m->{    /// <<__- 여기 들어오게 수정
				System.out.println(m.getUserId());
				System.out.println(m.getUserName());
			//	System.out.println(m.getUserImage());
				System.out.println(m.getUserEmail());
				return m;
			});
		}
		
	}

	@Override
	public String getUserId() {
		return this.id;
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
