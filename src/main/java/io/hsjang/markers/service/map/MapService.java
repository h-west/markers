package io.hsjang.markers.service.map;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.stereotype.Service;

@Service
public class MapService implements InitializingBean{

	Map<Integer, Distance> distanceMap;

	@Override
	public void afterPropertiesSet() throws Exception {
		distanceMap = new HashMap<Integer, Distance>();
		for(int i=1; i<=14; i++) {
			distanceMap.put(i, new Distance( 0.2*(1<<(14-i)), Metrics.KILOMETERS));
		}
	}
	
	public Distance getDistanceByZoom(int zoom) {
		return distanceMap.get(zoom);
	}
}
