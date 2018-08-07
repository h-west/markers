package io.hsjang.markers;

import java.nio.charset.Charset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import io.netty.handler.codec.http.HttpResponseStatus;
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
				.authorizeExchange().anyExchange().permitAll().and()
				.csrf().disable()
				.headers().disable()
				.exceptionHandling()
					.authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/login"))
					.accessDeniedHandler( (exchange,ex) -> {
				
						ServerHttpResponse response = exchange.getResponse();
						response.setStatusCode(HttpStatus.FORBIDDEN);
						DataBufferFactory dataBufferFactory = response.bufferFactory();
						DataBuffer buffer = dataBufferFactory.wrap("!!".getBytes());
						return response.writeWith(Mono.just(buffer))
								.doOnError( error -> DataBufferUtils.release(buffer));
				
//						return Mono.defer(() -> Mono.just(exchange.getResponse()))
//								.flatMap(response -> {
//									response.setStatusCode(this.httpStatus);
//									response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
//									DataBufferFactory dataBufferFactory = response.bufferFactory();
//									DataBuffer buffer = dataBufferFactory.wrap(e.getMessage().getBytes(
//										Charset.defaultCharset()));
//									return response.writeWith(Mono.just(buffer))
//										.doOnError( error -> DataBufferUtils.release(buffer));
//							});
						
					} ).and()
				.authenticationManager(auth->{
					return Mono.just(auth);
				})
				.build();
	}
}
