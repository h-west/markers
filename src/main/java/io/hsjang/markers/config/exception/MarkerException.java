package io.hsjang.markers.config.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarkerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	HttpStatus status;
	Map<String,Object> exception = new HashMap<String,Object>();
	
	public MarkerException(MarkerExceptionType type) {
		super(type.getMessage());
		this.status = type.getHttpStatus();
		exception.put("code", status.value());
		exception.put("message", type.getMessage());
	}
}