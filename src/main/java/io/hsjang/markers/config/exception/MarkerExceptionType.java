package io.hsjang.markers.config.exception;

import org.springframework.http.HttpStatus;

public enum MarkerExceptionType {

	UNKONWN(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	
	HttpStatus status;
	String message;
	
	private MarkerExceptionType(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return this.status;
	}
	
	public String getMessage() {
		return this.message;
	}
}
