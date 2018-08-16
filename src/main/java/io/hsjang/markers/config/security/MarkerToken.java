package io.hsjang.markers.config.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.hsjang.markers.domain.User;

public class MarkerToken implements Authentication {
	
	private static final long serialVersionUID = 1L;
	private static MACSigner SIGNER;
	private static MACVerifier VERIFIER;
	private static String SECRET = "abcdabcdabcdabcdabcdabcdabcdabcd";
	
	private Collection<GrantedAuthority> authorities;
	private User user;
	private boolean authenticated = false;
	
	static {
		try {
			SIGNER = new MACSigner(SECRET);
			VERIFIER = new MACVerifier(SECRET);
		} catch (JOSEException e) {
			e.printStackTrace();
		}
	}
	
	public static String of(User user) {
		SignedJWT jwt = new SignedJWT(
				new JWSHeader.Builder(JWSAlgorithm.HS256).build(), 
				new JWTClaimsSet.Builder()
					.claim("userId", user.getUserId())
					.claim("name", user.getName())
					.claim("email", user.getEmail())
					.claim("image", user.getImage())
					.claim("auths", user.getAuths())
					.issuer("markers")
					.issueTime(new Date())
				.build());
		try {
			jwt.sign(SIGNER);
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		return jwt.serialize(); 
	}
	
	public MarkerToken(String mkt) {
		try {
			SignedJWT jwt = SignedJWT.parse(mkt);
			user = new User(jwt.getPayload());
			authorities = new ArrayList<>();
			Arrays.stream(user.getAuths().split(",")).forEach(a->authorities.add( new SimpleGrantedAuthority("ROLE_"+a)));
			
			if(jwt.verify(VERIFIER)) {
				authenticated = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return user.getEmail();
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

}
