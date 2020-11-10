package com.stanbicibtc.AuthenticationService.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class UserInfoResponse {
	
	private String sub;
	private boolean email_verified;
	private String name;
	private String prefered_username;
	private String given_name;
	private String family_name;
	private String email;
	
}
