package io.hsjang.markers.domain.userprovider;

import java.util.List;

public interface UserProvider {

	String getUserId();
	String getName();
	String getImage();
	List<String> getAuths();
	
}
