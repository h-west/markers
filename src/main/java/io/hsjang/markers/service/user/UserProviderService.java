package io.hsjang.markers.service.user;

import io.hsjang.markers.domain.User;
import reactor.core.publisher.Mono;

public interface UserProviderService<T> {
	Mono<User> getUser(T t);
}
