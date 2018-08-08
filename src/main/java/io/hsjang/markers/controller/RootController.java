package io.hsjang.markers.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Mono;

@Controller
public class RootController {

	@RequestMapping("")
	public Mono<String> main() {
		return Mono.just("redirect:/main");
	}
	
	@RequestMapping("/{page}")
	public String page(@PathVariable String page) {
		return page;
	}
	
	@RequestMapping("/auth")
	@PreAuthorize("hasRole('USER')")
	public Mono<String> auth() {
		return Mono.just("main");
	}


}
