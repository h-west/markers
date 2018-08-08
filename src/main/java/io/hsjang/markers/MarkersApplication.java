package io.hsjang.markers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

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
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				/*.authorizeExchange()
					.anyExchange()
					.permitAll()
					.and()*/
				.csrf().disable()
				.headers().disable()
				.logout().disable()
				.requestCache().disable()
				.exceptionHandling()
					.authenticationEntryPoint( (exchange,e) -> 
						Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
					).and()
				.authenticationManager(auth->{
					return Mono.just(auth);
				})
				.addFilterAt((exchange,chain)->{
					return 
				}, SecurityWebFiltersOrder.AUTHENTICATION)
				.build();
	}
}
