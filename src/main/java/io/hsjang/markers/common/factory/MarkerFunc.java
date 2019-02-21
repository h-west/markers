package io.hsjang.markers.common.factory;

import java.util.Map;
import java.util.function.BiFunction;

import io.hsjang.markers.common.type.v2.MarkerType;
import io.hsjang.markers.domain.v2.Marker;

public interface  MarkerFunc<T> extends BiFunction<MarkerType, Map<String,Object>, Marker<T>> {
}