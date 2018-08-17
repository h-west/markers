package io.hsjang.markers.common.type;

public enum MarkerType {

	BOARD("./images/t1.svg"),
	TALK("./images/t3.svg"),
	DEFAULT("./images/d1.png");

	String icon;

	MarkerType(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}
	
	public static MarkerType getType(String type) {
		try {
			return valueOf(type.toUpperCase());
		}catch(IllegalArgumentException e) {
			return MarkerType.DEFAULT;
		}
	}
}
