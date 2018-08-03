package io.hsjang.markers.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.hsjang.markers.domain.User;
import io.hsjang.markers.domain.userprovider.FacebookUserProvider;
import io.hsjang.markers.repository.UserRepository;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	@Autowired
	UserRepository userRepository;

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
	public Mono<User> fbLogin(Mono<FacebookUserProvider> auth){
		return auth.flatMap(a->{
			return userRepository.save(new User(a));
		});
	}
	
	@PostMapping("/login/fbtest")
	public void fbLogintest(@RequestParam Map<String,Object> param){
		System.out.println("####################");
	}
}
