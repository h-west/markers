package io.hsjang.markers.service.user.provider;

import org.springframework.web.reactive.function.client.WebClient;

import io.hsjang.markers.service.user.UserInfo;
import reactor.core.publisher.Mono;

public interface UserInfoProvider {
	
	Mono<UserInfo> getUserInfo(WebClient client);
}
