package io.hsjang.markers.controller.api;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.hsjang.markers.domain.User;
import io.hsjang.markers.repository.UserRepository;
import io.hsjang.markers.service.user.provider.UserInfoProvider;
import io.hsjang.markers.service.user.provider.facebook.FacebookUserInfoProvider;
import io.hsjang.markers.service.user.provider.facebook.FacebookUserProvider;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class LoginController implements InitializingBean{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FacebookUserProvider facebookUserProvider;
	
	WebClient fbClient;
	final String FACEBOOK_GRAPH_URL = "https://graph.facebook.com";
	
	@Override
	public void afterPropertiesSet() throws Exception {
		fbClient = WebClient.create(FACEBOOK_GRAPH_URL);
	}
	

//	@PostMapping("/login/{type}")
//	public Mono<User> fbLogin(@PathVariable Mono<String> type ){
//		return type.flatMap(t->{
//			UserProvider provider = UserProvider.findByUserProvider(t);
//			switch(provider) {
//				case FACEBOOK: 
//					return userRepository.save(new User(provider, ));
//					break;
//				case EMPTY:
//				default:
//					throw new RuntimeException();
//			}
//		});
//	}
	
	@PostMapping("/login/fb")
	public Mono<User> fbLogin(Mono<FacebookUserInfoProvider> userInfoProvider){
		 return userInfoProvider.flatMap(facebookUserProvider::getUser);
	}
	
	
	@RequestMapping("/login/fbtest")
	@ResponseBody
	public String fbLogintest(@RequestParam Map<String,Object> param){
		System.out.println("********************* params ************************");
		param.forEach((k,v)->System.out.println(k + " => " +v ));
		return "";
	}

	
}
