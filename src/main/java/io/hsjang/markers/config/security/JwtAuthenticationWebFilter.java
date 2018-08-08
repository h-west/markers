package io.hsjang.markers.config.security;

import java.util.Collection;

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
			
			
			
			
			return Mono.just(new Authentication() {
				
				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean isAuthenticated() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public Object getPrincipal() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object getDetails() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object getCredentials() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					// TODO Auto-generated method stub
					return null;
				}
			});
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

}
