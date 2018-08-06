package io.hsjang.markers.service.user.provider.facebook;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.hsjang.markers.domain.User;
import io.hsjang.markers.service.user.UserProvider;
import io.hsjang.markers.service.user.provider.UserInfoProvider;
import reactor.core.publisher.Mono;

@Service
public class FacebookUserProvider implements UserProvider,InitializingBean {

	WebClient fbClient;
	String FACEBOOK_GRAPH_HOST = "https://graph.facebook.com/v3.1";

	@Value("${facebook.client-id}")
	String fbClientId;

	@Value("${facebook.client-secret}")
	String fbClientSecret;

	@Override
	public void afterPropertiesSet() throws Exception {
		fbClient = WebClient.create(FACEBOOK_GRAPH_HOST);
	}

	@Override
	public Mono<User> getUser(UserInfoProvider provider) {
		return provider.getUserInfo(fbClient).map(User::new);
	}

	// @formatter:off
	/*
	 FACEBOOK 앱 엑세스 토큰조회	
	// https://graph.facebook.com/me?fields=id,name,email
	// https://graph.facebook.com/v3.1/me?access_token=aaa|aaa&debug=all&fields=id,name&format=json&method=get&pretty=0&suppress_http_code=1
	 
	 1. curl -X GET "https://graph.facebook.com/oauth/access_token
		  ?client_id=your-app-id
		  &client_secret=your-app-secret
		  &redirect_uri=your-redirect-url
		  &grant_type=client_credentials"
		  
	 2. curl -X GET "https://graph.facebook.com/your-endpoint?key=value&access_token=your-app_id|your-app_secret"
	 
	 */
	// @formatter:on

}
