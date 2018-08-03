package io.hsjang.markers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class MarkersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarkersApplication.class, args);
	}
	
	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http
			.authorizeExchange()
				.anyExchange().permitAll()
				.and()
				.csrf().disable()
			.build();
	}
}
