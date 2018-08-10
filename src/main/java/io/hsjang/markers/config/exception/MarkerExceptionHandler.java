package io.hsjang.markers.config.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MarkerExceptionHandler implements ErrorWebExceptionHandler{
	
	private DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
	private ObjectMapper mapper = new ObjectMapper(); 
	
	Map<HttpStatus,Object> definedResult;
	Flux<Map<Class<?>,BiFunction<ServerWebExchange,Throwable,Mono<Void>>>> throwableHandler;
	
	public MarkerExceptionHandler() {
		
		definedResult = new HashMap<HttpStatus,Object>();
		Map<String,Object> unauthorized = new HashMap<String,Object>();
		unauthorized.put("code", HttpStatus.UNAUTHORIZED.value());
		unauthorized.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
		
		definedResult.put(HttpStatus.UNAUTHORIZED, unauthorized);
		
		
		List<Map<Class<?>,BiFunction<ServerWebExchange,Throwable,Mono<Void>>>> throwableHandlerList = new ArrayList<Map<Class<?>,BiFunction<ServerWebExchange,Throwable,Mono<Void>>>>();
		Map<Class<?>,BiFunction<ServerWebExchange,Throwable,Mono<Void>>> handler = new HashMap<Class<?>,BiFunction<ServerWebExchange,Throwable,Mono<Void>>>();
		
		handler.put(MarkerException.class, (exchange,ex)->{
			try {
				MarkerException me = (MarkerException) ex;
				ServerHttpResponse response = exchange.getResponse();
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
				response.setStatusCode(me.getStatus());
				DataBuffer buf = dataBufferFactory.wrap(mapper.writeValueAsBytes(me.getException()));
				return response.writeWith(Flux.just(buf));
			} catch (Exception e) {
				ex.printStackTrace();
				return Mono.error(ex);
			}
		});
		
		handler.put(AccessDeniedException.class, (exchange,ex)->{
			try {
				ServerHttpResponse response = exchange.getResponse();
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				DataBuffer buf = dataBufferFactory.wrap(mapper.writeValueAsBytes(definedResult.get(HttpStatus.UNAUTHORIZED)));
				return response.writeWith(Flux.just(buf));
			} catch (Exception e) {
				ex.printStackTrace();
				return Mono.error(ex);
			}
		});
		
		throwableHandlerList.add(handler);
		throwableHandler = Flux.fromIterable(throwableHandlerList);
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		return throwableHandler
			.filter(m -> m.containsKey(ex.getClass()))
			.next()
			.map(m-> m.get(ex.getClass()))
			.flatMap(f->f.apply(exchange, ex));
	}
}
