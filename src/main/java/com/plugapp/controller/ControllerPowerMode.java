package com.plugapp.controller;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plugapp.MainActivity;
import com.plugapp.R;
import com.plugapp.constants.GlobalConstants;
import com.plugapp.container.RefreshToken;
import com.plugapp.container.Switch;
import com.plugapp.retrofit.AccessToken;
import com.plugapp.retrofit.PlugAPI;
import com.plugapp.retrofit.RetrofitInstance;
import com.plugapp.utils.ErrorUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerPowerMode implements Callback<Switch> {

    private ProgressDialog pd;
    private MainActivity activity = null;

    public ControllerPowerMode(MainActivity activity) {
            this.activity = activity;
    }

    public Call<Switch> start(String status) {
        // Creación del dialogo
        pd = new ProgressDialog(activity);
        pd.setMessage(activity.getResources().getString(R.string.getStatus));
        pd.setCancelable(false);
        pd.show();

        PlugAPI gerritAPI = RetrofitInstance.getInstance().create(PlugAPI.class);


        Call<Switch> call = gerritAPI.switchPowerMode(status);
        call.enqueue(this);

        return call;

    }

    @Override
    public void onResponse(Call<Switch> call, Response<Switch> response) {

        // If token isn't correct, this will be a refresh the token
        if(!response.isSuccessful()) {
            // TODO Refactor Error control

            Log.d("tokenError", ErrorUtils.transformErrorURL(response));

        } else {
            final ToggleButton switchButton = (ToggleButton) activity.findViewById(R.id.switchTB);

            if ("ON".equals(response.body().getStatusPlug())) {
                switchButton.setChecked(true);
            } else if ("OFF".equals(response.body().getStatusPlug())) {
                switchButton.setChecked(false);
            }
        }

        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    public void onFailure(Call<Switch> call, Throwable t) {
        t.printStackTrace();
    }

}