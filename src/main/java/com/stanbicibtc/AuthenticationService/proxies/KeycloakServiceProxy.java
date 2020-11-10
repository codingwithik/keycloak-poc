package com.stanbicibtc.AuthenticationService.proxies;

import com.stanbicibtc.AuthenticationService.pojo.UserInfoResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface KeycloakServiceProxy {
	
	final static String USER_INFO = "userinfo";
	
    @POST(USER_INFO)
    public Call<UserInfoResponse> getUserInfo();
    

}
