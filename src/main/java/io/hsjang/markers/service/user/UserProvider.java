package io.hsjang.markers.service.user;

import io.hsjang.markers.domain.User;
import io.hsjang.markers.service.user.provider.UserInfoProvider;
import reactor.core.publisher.Mono;

public interface UserProvider {
	
	Mono<User> getUser(UserInfoProvider provider);
}
