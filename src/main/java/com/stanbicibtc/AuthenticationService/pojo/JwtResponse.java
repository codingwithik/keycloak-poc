package com.stanbicibtc.AuthenticationService.pojo;

import lombok.Data;

@Data
public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private String email;
	private long expiration;

	public JwtResponse(String accessToken, String email, long expiration) {
		this.token = accessToken;
		this.email = email;
		this.expiration = expiration;
	}
}
