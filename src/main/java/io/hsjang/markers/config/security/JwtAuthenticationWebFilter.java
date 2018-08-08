package io.hsjang.markers.config.security;

import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

	String headerOrParamKey = "mkt";

	public JwtAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub

		
		setAuthenticationConverter(exchange->{
			Mono<ServerHttpRequest> request = Mono.just(exchange).map(ServerWebExchange::getRequest);
			return request
				.map(ServerHttpRequest::getHeaders)
				.filter(h -> h.containsKey(headerOrParamKey))
				.map(h -> h.getFirst(headerOrParamKey))
				.flatMap(JwtAuthenticationWebFilter.this::getAuthentication)
				.switchIfEmpty(
					request
					.map(ServerHttpRequest::getQueryParams)
					.filter(q -> q.containsKey(headerOrParamKey))
					.map(q ->q.getFirst(headerOrParamKey))
					.flatMap(JwtAuthenticationWebFilter.this::getAuthentication)
					.switchIfEmpty(Mono.just(null))
				);
		});
		
		setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler((exchange, ex)->
			Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
		));
		
		setRequiresAuthenticationMatcher(exchange -> {
			Mono<ServerHttpRequest> request = Mono.just(exchange).map(ServerWebExchange::getRequest);
			return request
					.map(ServerHttpRequest::getHeaders)
					.filter(h -> h.containsKey(headerOrParamKey))
					.flatMap(h -> MatchResult.match())
					.switchIfEmpty(
						request
						.map(ServerHttpRequest::getQueryParams)
						.filter(q -> q.containsKey(headerOrParamKey))
						.flatMap(q -> MatchResult.match())
						.switchIfEmpty(MatchResult.notMatch())
					);
		});
	}
	
	public Mono<Authentication> getAuthentication(String token){
		return Mono.just(null);
	}

}
