package io.hsjang.markers.common.model;

import java.util.Arrays;
import java.util.LinkedHashMap;

import reactor.core.publisher.Mono;

public class FormMap extends LinkedHashMap<String,Object>{

	private static final long serialVersionUID = 1L;
	
	public <T> Mono<T> to(Class<T> clazz) {
		try {
			T o = clazz.newInstance();
			Arrays.stream(clazz.getDeclaredFields())
			.filter(f->containsKey(f.getName()))
			.forEach(f->{
				String fieldName = f.getName();
				String setter = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
				try {
					clazz.getMethod(setter, f.getType()).invoke(o, get(fieldName));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return Mono.just(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
