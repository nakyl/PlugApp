package com.plugapp.controller;

import android.app.ProgressDialog;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plugapp.MainActivity;
import com.plugapp.R;
import com.plugapp.constants.GlobalConstants;
import com.plugapp.container.Status;
import com.plugapp.retrofit.PlugAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerStatus implements Callback<Status> {

    private ProgressDialog pd;
    private MainActivity activity = null;

    public ControllerStatus(MainActivity activity) {
            this.activity = activity;
    }

    public Call<Status> start() {

        // Creación del dialogo
        pd = new ProgressDialog(activity);
        pd.setMessage("Actualizando");
        pd.setCancelable(false);
        pd.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PlugAPI gerritAPI = retrofit.create(PlugAPI.class);

        Call<Status> call = gerritAPI.loadStatus();
        call.enqueue(this);

        return call;

    }

    @Override
    public void onResponse(Call<Status> call, Response<Status> response) {
        if(response.isSuccessful()) {
            Status statusList = response.body();
        } else {
            System.out.println(response.errorBody());
        }

        if (pd.isShowing()){
            pd.dismiss();
        }

        final ToggleButton switchButton = (ToggleButton) activity.findViewById(R.id.switchTB);

        if("ON".equals(response.body().getStatus())) {
            switchButton.setChecked(true);
        } else if("OFF".equals(response.body().getStatus())) {
            switchButton.setChecked(false);
        }
    }

    @Override
    public void onFailure(Call<Status> call, Throwable t) {
        t.printStackTrace();
    }
}