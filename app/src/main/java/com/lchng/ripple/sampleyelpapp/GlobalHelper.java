package com.lchng.ripple.sampleyelpapp;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

public final class GlobalHelper {
    private GlobalHelper() {
        throw new UnsupportedOperationException();
    }

    public static <T> T convertHttpResponseToModelResponse(Response results, Class<T> classOfT) {
        Gson gson = new Gson();
        T restaurantResponse = null;

        try (ResponseBody responseBody = results.body()){
            String response = responseBody.string();
            Log.d(GlobalHelper.class.getName(), "onSuccess: " + response);

            restaurantResponse = gson.fromJson(response, classOfT);
        } catch (IOException e) {
            // Fail to parse data which imply logic error or incorrect data or data structure error
            e.printStackTrace();
        }

        return restaurantResponse;
    }
}
