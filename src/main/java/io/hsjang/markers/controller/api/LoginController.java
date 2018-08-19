package io.hsjang.markers.controller.api;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import io.hsjang.markers.config.security.MarkerToken;
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
					ServerHttpResponse response = exchange.getResponse();
					response.setStatusCode(HttpStatus.CREATED);
					response.addCookie(
						ResponseCookie.from("mkt", MarkerToken.of(u))
						.maxAge(Duration.ofDays(9999))
						.path("/")
						.secure(true)
						.build()
					);
					//response.getHeaders().setLocation(new URI("/main"));
					return u;
				});
		
		// @formatter:on
	}
	
	public static void main(String[] args) {
		String aa = "asd.exe";
		System.out.println(aa.substring( aa.lastIndexOf(".") ));
	}

}
