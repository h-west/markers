package io.hsjang.markers.domain.v2;

import java.util.HashMap;

import io.hsjang.markers.common.type.v2.MarkerType;

public abstract class MarkerDetail extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public static MarkerDetail getDetailsInstance(MarkerType type) {

		switch (type.getNode1()) {
		case MARKER:
			return new MarkerDetails();
		case LOCAL:
			return new LocalDetails();
		default:
			return new DefaultDetails();
		}
	}
}

class MarkerDetails extends MarkerDetail {

	private static final long serialVersionUID = 1L;
}

class LocalDetails extends MarkerDetail {

	private static final long serialVersionUID = 1L;
}

class DefaultDetails extends MarkerDetail {

	private static final long serialVersionUID = 1L;
}
