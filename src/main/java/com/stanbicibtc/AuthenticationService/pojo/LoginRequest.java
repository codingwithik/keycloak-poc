package com.stanbicibtc.AuthenticationService.pojo;

import lombok.Data;

@Data
public class LoginRequest {

	private String username;
	private String password;
	private String tokenId;
}
