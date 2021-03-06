package com.plugapp.controller;

import android.app.ProgressDialog;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plugapp.MainActivity;
import com.plugapp.R;
import com.plugapp.constants.GlobalConstants;
import com.plugapp.container.Switch;
import com.plugapp.retrofit.AccessToken;
import com.plugapp.retrofit.PlugAPI;
import com.plugapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
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
        pd.setMessage("Actualizando");
        pd.setCancelable(false);
        pd.show();

        PlugAPI gerritAPI = RetrofitInstance.getInstance().create(PlugAPI.class);

        Call<Switch> call = gerritAPI.switchPowerMode(status, AccessToken.getInstance().getAccessToken());
        call.enqueue(this);

        return call;

    }

    @Override
    public void onResponse(Call<Switch> call, Response<Switch> response) {

        if(!response.isSuccessful()) {
            System.out.println(response.errorBody());
            // TODO Cambiar
        }

        if (pd.isShowing()){
            pd.dismiss();
        }

        final ToggleButton switchButton = (ToggleButton) activity.findViewById(R.id.switchTB);

        if("ON".equals(response.body().getStatusPlug())) {
            switchButton.setChecked(true);
        } else if("OFF".equals(response.body().getStatusPlug())) {
            switchButton.setChecked(false);
        }
    }

    @Override
    public void onFailure(Call<Switch> call, Throwable t) {
        t.printStackTrace();
    }
}