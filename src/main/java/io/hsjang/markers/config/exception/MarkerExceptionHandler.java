package io.hsjang.markers.config.exception;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MarkerExceptionHandler implements ErrorWebExceptionHandler{
	
	private DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		
		if(ex instanceof AccessDeniedException) {
			
			
			ServerHttpResponse response = exchange.getResponse();
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			DataBuffer buf = dataBufferFactory.wrap("Hello from exchange".getBytes(StandardCharsets.UTF_8));
			return response.writeWith(Flux.just(buf));
		}
		else if(ex instanceof IllegalStateException) {
			
			ServerHttpResponse response = exchange.getResponse();
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			DataBuffer buf = dataBufferFactory.wrap("IllegalStateException  ".getBytes(StandardCharsets.UTF_8));
			return response.writeWith(Flux.just(buf));
		}
		else {
			return Mono.error(ex);
		}
	}

	/*public MarkerExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
			ApplicationContext applicationContext) {
		super(errorAttributes, resourceProperties, applicationContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	


}
