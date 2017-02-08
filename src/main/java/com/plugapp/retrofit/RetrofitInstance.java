package com.plugapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plugapp.constants.GlobalConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nakyl on 08/02/2017.
 */

public final class RetrofitInstance {

    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {

        if(retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
