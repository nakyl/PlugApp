package com.plugapp.controller;

import android.app.ProgressDialog;
import android.util.Base64;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plugapp.MainActivity;
import com.plugapp.R;
import com.plugapp.constants.GlobalConstants;
import com.plugapp.container.OauthToken;
import com.plugapp.container.Status;
import com.plugapp.retrofit.AccessToken;
import com.plugapp.retrofit.PlugAPI;
import com.plugapp.retrofit.RetrofitInstance;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerToken implements Callback<OauthToken> {

    private MainActivity mainActivity;
    private ProgressDialog pd;
    private boolean isEnd = false;

    public ControllerToken(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Call<OauthToken> createToken() {

        // Create Dialog
        pd = new ProgressDialog(mainActivity);
        pd.setMessage(mainActivity.getResources().getString(R.string.getToken));
        pd.setCancelable(false);
        pd.show();

        PlugAPI gerritAPI = RetrofitInstance.getInstanceToken().create(PlugAPI.class);

        // Call api oauth/token: Basic Auth + username + password + grant type
        Call<OauthToken> call = gerritAPI.getToken("Basic " + Base64.encodeToString("admin:admin".getBytes(), Base64.NO_WRAP), "admin", "admin", "password");
        call.enqueue(this);

        return call;

    }

    public Call<OauthToken> refreshToken() {

        PlugAPI gerritAPI = RetrofitInstance.getInstanceToken().create(PlugAPI.class);

        // Call api oauth/token: Basic Auth + refresh token + grant type
        Call<OauthToken> call = gerritAPI.getTokenRefresh("Basic " + Base64.encodeToString("admin:admin".getBytes(), Base64.NO_WRAP), "refresh_token", AccessToken.getInstance().getRefreshToken());
        call.enqueue(this);

        return call;

    }

    @Override
    public void onResponse(Call<OauthToken> call, Response<OauthToken> response) {
        OauthToken oauthToken = null;

        if(response.isSuccessful()) {
            oauthToken = response.body();
        }

        // Set the new token information
        AccessToken.setOauthToken(oauthToken);

        // Close the dialog
        if(mainActivity != null) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }

        // Flag this process as finished
        isEnd = true;
    }

    @Override
    public void onFailure(Call<OauthToken> call, Throwable t) {
        t.printStackTrace();
    }

    public boolean isEnd() {
        return isEnd;
    }
}