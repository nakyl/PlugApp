package com.plugapp.retrofit;

import com.plugapp.container.OauthToken;
import com.plugapp.container.Status;
import com.plugapp.container.Switch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PlugAPI {

    @GET("status")
    Call<Status> loadStatus();

    @GET("switch")
    Call<Switch> switchPowerMode(@Query("changeState") String status, @Query("access_token") String accessToken);

    @POST("oauth/token")
    Call<OauthToken> getToken(@Header("Authorization") String authorization, @Query("username") String username, @Query("password") String password, @Query("grant_type") String grant_type);
}