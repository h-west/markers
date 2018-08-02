package io.hsjang.markers.type;

public enum GeometryType {

	POINT("Point"),
	LINE_STRING("LineString"),
	POLYGON("Polygon"),
	POLYGON_WITH_HOLE("Polygon"),
	MULTI_POINT("MultiPoint"),
	MULTI_LINE_STRING("MultiLineString"),
	MULTI_POLYGON("MultiPolygon"),
	MULTI_POLYGON_WITH_HOLE("MultiPolygon");
	
	String type;
	
	GeometryType(String type){
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
}