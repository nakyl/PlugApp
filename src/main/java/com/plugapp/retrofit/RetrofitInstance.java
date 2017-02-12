package com.plugapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plugapp.constants.GlobalConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nakyl on 08/02/2017.
 */

public final class RetrofitInstance {

    private static Retrofit retrofit = null;
    private static Retrofit retrofitToken = null;

    public static Retrofit getInstance() {

        if(retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new HeaderInterceptor());  // <-- this is the important line!

            retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getInstanceToken() {

        if(retrofitToken == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofitToken = new Retrofit.Builder()
                    .baseUrl(GlobalConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofitToken;
    }
}
