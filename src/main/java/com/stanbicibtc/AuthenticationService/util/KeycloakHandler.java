package com.stanbicibtc.AuthenticationService.util;

import com.stanbicibtc.AuthenticationService.generator.KeycloakGenerator;
import com.stanbicibtc.AuthenticationService.pojo.UserInfoResponse;
import com.stanbicibtc.AuthenticationService.proxies.KeycloakServiceProxy;
import retrofit2.Call;
import retrofit2.Response;


public class KeycloakHandler {
	
	public UserInfoResponse validateToken(String token) {
		
		KeycloakServiceProxy service = KeycloakGenerator.createService(KeycloakServiceProxy.class, token);

		Call<UserInfoResponse> callAsync = service.getUserInfo();

		try {
			Response<UserInfoResponse> response = callAsync.execute();

			return response.body();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
