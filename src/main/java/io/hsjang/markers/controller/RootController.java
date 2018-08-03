package io.hsjang.markers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("/favicon.ico")
	@ResponseBody
	public String favicon() {
		return "";
	}
}
