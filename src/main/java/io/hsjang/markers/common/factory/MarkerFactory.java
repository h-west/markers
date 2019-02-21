package io.hsjang.markers.common.factory;

import java.util.Map;

import io.hsjang.markers.common.type.v2.MarkerType;
import io.hsjang.markers.domain.v2.Marker;

public class MarkerFactory {

	
	public static <T> Marker<T> build(MarkerType type, Map<String, Object> map) {
		MarkerFunc<T> f = (t, m)->{
			Marker<T> marker = new Marker<T>(type, map);
			switch (type.getNode1()) {
				case MARKER:
				case LOCAL:
					
				default:
			}
			return marker;
		};
		return f.apply(type, map);
	}
}
