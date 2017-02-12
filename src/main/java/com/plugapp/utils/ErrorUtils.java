package com.plugapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Response;

/**
 * Created by Nakyl on 09/02/2017.
 */

public final class ErrorUtils {

    public static String transformErrorURL(Response<?> response) {
        final StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       return sb.toString();
    }
}
