package io.hsjang.markers.type;

public enum MarkerType {

	BOARD("./images/t1.svg"),
	DEFAULT("./images/t1.svg");

	String icon;

	MarkerType(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}
	
	public static MarkerType getType(String type) {
		return valueOf(type.toUpperCase());
	}
}
