package io.hsjang.markers.controller.api;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import io.hsjang.markers.domain.User;
import io.hsjang.markers.repository.UserRepository;
import io.hsjang.markers.service.user.provider.facebook.FacebookUserInfoProvider;
import io.hsjang.markers.service.user.provider.facebook.FacebookUserProvider;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FacebookUserProvider facebookUserProvider;
	
	@PostMapping("/login/fb")
	public Mono<User> fbLogin(Mono<FacebookUserInfoProvider> userInfoProvider, ServerWebExchange exchange) {
		// @formatter:off
		
		return userInfoProvider
				.flatMap(facebookUserProvider::getUser)
				.flatMap(userRepository::save)
				.map(u->{
					exchange.getResponse()
					.addCookie(
							ResponseCookie.from("mkt", "jtw")
							.maxAge(Duration.ofDays(1))
							.path("/")
							.secure(true)
							.build()
					);
					return u;
				});
		
		// @formatter:on
	}

}
