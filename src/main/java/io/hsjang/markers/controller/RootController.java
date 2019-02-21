package io.hsjang.markers.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.hsjang.markers.common.type.v2.MarkerType;
import reactor.core.publisher.Mono;

@Controller
public class RootController {

	@RequestMapping("")
	public Mono<String> main() {
		return Mono.just("redirect:/main");
	}
	
	@RequestMapping("/{page}")
	public String page(@PathVariable String page) {
		//MarkerType.LOCAL_01.getChildren()
		System.out.println(MarkerType.LOCAL_01_01.getChildren());
		return page;
	}
	
	@RequestMapping("/auth")
	@PreAuthorize("hasRole('USER')")
	public Mono<String> auth(@AuthenticationPrincipal String userId) {
		System.out.println(userId);
		return Mono.just("main");
	}


}
