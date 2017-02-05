package com.plugapp.retrofit;

import com.plugapp.container.Status;
import com.plugapp.container.Switch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlugAPI {

    @GET("status")
    Call<Status> loadStatus();

    @GET("switch")
    Call<Switch> switchPowerMode(@Query("changeState") String status);
}