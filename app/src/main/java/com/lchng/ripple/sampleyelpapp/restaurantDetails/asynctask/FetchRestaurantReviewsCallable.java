package com.lchng.ripple.sampleyelpapp.restaurantDetails.asynctask;

import android.util.Log;

import com.lchng.ripple.sampleyelpapp.GlobalConfig;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchRestaurantReviewsCallable implements Callable<Response> {
    private final String restaurantId;

    public FetchRestaurantReviewsCallable(String restaurantId) {
        this.restaurantId = restaurantId;
    }



    @Override
    public Response call() {
        OkHttpClient client = new OkHttpClient();
        Request request = constructRequest(GlobalConfig.YELP_FETCH_BUSINESS_DETAIL_REVIEWS_URL, this.restaurantId);

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("FetchRestaurantReviewsCallable onFailure Unexpected code " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getName(), "FetchRestaurantReviewsCallable onSuccess");
        return response;
    }

    private Request constructRequest(String urlPath, String restaurantId) {
        final String HEADER_KEY = GlobalConfig.YELP_API_HEADER_KEY;
        final String HEADER_VALUE = GlobalConfig.YELP_API_HEADER_VALUE;

        HttpUrl.Builder httpBuilder = HttpUrl.parse(String.format(urlPath, restaurantId)).newBuilder();

        return new Request.Builder().url(httpBuilder.build()).header(HEADER_KEY, HEADER_VALUE).get().build();
    }
}
