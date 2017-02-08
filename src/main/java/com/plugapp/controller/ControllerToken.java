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

    private ProgressDialog pd;
    private MainActivity activity = null;

    public ControllerToken(MainActivity activity) {
            this.activity = activity;
    }

    public Call<OauthToken> start() {

        // Creación del dialogo
        pd = new ProgressDialog(activity);
        pd.setMessage("Obteniendo credenciales");
        pd.setCancelable(false);
        pd.show();

        PlugAPI gerritAPI = RetrofitInstance.getInstance().create(PlugAPI.class);

        Call<OauthToken> call = gerritAPI.getToken("Basic " + Base64.encodeToString("admin:admin".getBytes(), Base64.NO_WRAP), "admin", "admin", "password");
        call.enqueue(this);

        return call;

    }

    @Override
    public void onResponse(Call<OauthToken> call, Response<OauthToken> response) {
        OauthToken oauthToken = null;

        if(response.isSuccessful()) {
            oauthToken = response.body();
        } else {
            System.out.println(response.errorBody());
        }

        if (pd.isShowing()){
            pd.dismiss();
        }

        AccessToken.setOauthToken(oauthToken);
    }

    @Override
    public void onFailure(Call<OauthToken> call, Throwable t) {
        t.printStackTrace();
    }
}