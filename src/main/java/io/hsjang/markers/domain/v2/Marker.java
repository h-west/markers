package io.hsjang.markers.domain.v2;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import io.hsjang.markers.common.type.v2.MarkerType;
import io.hsjang.markers.config.view.JsonViewConfig;
import lombok.Data;

@Document
@Data
public class Marker<T> {

	@Id
	String id;
	String type = "Feature";
	T geometry;
	String title;
	String icon;
	MarkerType markerType;

	@JsonView(JsonViewConfig.WithDetails.class)
	Map<String,Object> details;

	public Marker() {};
	@SuppressWarnings("unchecked")
	public Marker(MarkerType markerType, Map<String,Object> details) {
		this.geometry = (T) details.get("geometry");
		this.title = details.get("title").toString();
		this.icon = "::" + markerType.name();
		this.markerType = markerType;
		this.details = details;
	}
}
