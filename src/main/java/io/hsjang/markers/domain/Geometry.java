package io.hsjang.markers.domain;

import org.assertj.core.util.Arrays;

import io.hsjang.markers.type.GeometryType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Geometry {

	String type;
	Object[] coordinates;
	
	public static Geometry builder(GeometryType type, Object[]coordinates) {
		return new Geometry(type.getType(), coordinates); 
	}
	public static Geometry point(double lat, double lng) {
		return new Geometry(GeometryType.POINT.getType(), Arrays.array(lat,lng)); 
	}
}
