package io.hsjang.markers.config.result;

import org.springframework.http.HttpStatus;

public class Result {

	HttpStatus status;
	Object data;
	
	public Result(Object data) {
		this(HttpStatus.OK, data);
	}
	
	public Result(HttpStatus status, Object data) {
		this.status = status;
		this.data = data;
	}
}
