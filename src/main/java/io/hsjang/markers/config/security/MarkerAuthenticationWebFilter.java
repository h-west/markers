package io.hsjang.markers.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher.MatchResult;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MarkerAuthenticationWebFilter extends AuthenticationWebFilter {

	String headerOrParamKey = "mkt";

	public MarkerAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
		super(authentication->Mono.just(authentication));
		
		setAuthenticationConverter(exchange->{
			Mono<ServerHttpRequest> request = Mono.just(exchange).map(ServerWebExchange::getRequest);
			Mono<Authentication> cookie = request
					.map(ServerHttpRequest::getCookies)
					.filter(c -> c.containsKey(headerOrParamKey))
					.map(c -> c.getFirst(headerOrParamKey).getValue())
					.map(MarkerToken::new);
			Mono<Authentication> header = request
					.map(ServerHttpRequest::getHeaders)
					.filter(h -> h.containsKey(headerOrParamKey))
					.map(h -> h.getFirst(headerOrParamKey))
					.map(MarkerToken::new);
			return Flux.concat(cookie,header).next(); //.defaultIfEmpty(defaultV);
			/*return 
				.switchIfEmpty(
					request
					.map(ServerHttpRequest::getHeaders)
					.filter(h -> h.containsKey(headerOrParamKey))
					.map(h -> h.getFirst(headerOrParamKey))
					.map(this::getAuthentication)
					.switchIfEmpty(Mono.empty())
				);*/
		});
		
		setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler((exchange, ex)->
			Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
		));
		
		setRequiresAuthenticationMatcher(exchange -> {
			Mono<ServerHttpRequest> request = Mono.just(exchange).map(ServerWebExchange::getRequest);
			
			
			//return Flux.concat(cookie,header).next();
			return request
					.map(ServerHttpRequest::getHeaders)
					.filter(h -> h.containsKey(headerOrParamKey))
					.flatMap(h -> MatchResult.match())
					.switchIfEmpty(
						request
						.map(ServerHttpRequest::getCookies)
						.filter(c -> c.containsKey(headerOrParamKey))
						.flatMap(c -> MatchResult.match())
						.switchIfEmpty(MatchResult.notMatch())
					);
		});
	}
	

}
