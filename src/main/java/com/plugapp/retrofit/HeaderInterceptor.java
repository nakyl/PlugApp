package com.plugapp.retrofit;

import com.plugapp.controller.ControllerToken;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.Query;

public class HeaderInterceptor
        implements Interceptor {
    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();

        if(AccessToken.needRefresh()) {
            ControllerToken token = new ControllerToken(null);
            token.refreshToken();

            // While the process is not end, this thread going to sleep
            while (!token.isEnd()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Add token parameter to url method GET. Necessary for all calls
        HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("access_token", AccessToken.getInstance().getAccessToken()).build();


        return chain.proceed(request.newBuilder().url(url).build());
    }

}