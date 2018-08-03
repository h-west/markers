package io.hsjang.markers.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.hsjang.markers.domain.User;
import io.hsjang.markers.domain.oauth2.FacebookOAuth2Response;
import reactor.core.publisher.Mono;

@Service
public class FacebookUserProviderService implements UserProviderService<FacebookOAuth2Response>,InitializingBean {

	WebClient fbClient;
	String FACEBOOK_GRAPH_HOST = "https://graph.facebook.com/v3.1";
	String appAccessToken;
	Map<String, String> meParamMap;

	@Value("${facebook.client-id}")
	String fbClientId;

	@Value("${facebook.client-secret}")
	String fbClientSecret;

	@Override
	public void afterPropertiesSet() throws Exception {
		fbClient = WebClient.create(FACEBOOK_GRAPH_HOST);
		appAccessToken = fbClientId + "|" + fbClientSecret;

//		meParamMap = new HashMap<>();
//		meParamMap.put("fields", "id,name,email");
//		meParamMap.put("access_token", appAccessToken);

	}

	// https://graph.facebook.com/me?fields=id,name,email
	// https://graph.facebook.com/v3.1/me?access_token=aaa|aaa&debug=all&fields=id,name&format=json&method=get&pretty=0&suppress_http_code=1
	@Override
	public Mono<User> getUser(FacebookOAuth2Response oauth2) {
//		String inputToken = oauth2.getAccessToken();
//		fbClient.method(HttpMethod.GET).uri("/debug_token?input_token="+inputToken+"&access_token="+appAccessToken).exchange()
//				.filter(r->{r.body})
//				.
//		
//		return fbClient.method(HttpMethod.GET).uri("/me?fields=id,name,email,picture&access_token=" + ).exchange()
//				.flatMap(r->{
//					
//					/* ObjectMapper mapper = new ObjectMapper();
//					try {
//						System.out.println(mapper.writeValueAsString(r));
//					} catch (JsonProcessingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}*/
//					
//					return r.bodyToMono(Map.class).map(m->{
//						
//						// 결과 출력
//						m.forEach((k,v)->System.out.println(k + " => " +v ));
//						
//						return new User();
//					});
//					
//					//return Mono.just(new User());
//					// 
//				});
		
		return Mono.just(new User());
	}

	// @formatter:off
	/*
	 FACEBOOK 앱 엑세스 토큰조회
	 
	 1. curl -X GET "https://graph.facebook.com/oauth/access_token
		  ?client_id=your-app-id
		  &client_secret=your-app-secret
		  &redirect_uri=your-redirect-url
		  &grant_type=client_credentials"
		  
	 2. curl -X GET "https://graph.facebook.com/your-endpoint?key=value&access_token=your-app_id|your-app_secret"
	 
	 */
	// @formatter:on

}
