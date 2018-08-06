package com.lchng.ripple.sampleyelpapp.home.asynctask;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.lchng.ripple.sampleyelpapp.GlobalConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveRestaurantDataAsyncTask extends AsyncTask<String, String, Response> {
    private final IRestaurantResultCallback callback;
    private final String keyword;

    private final String location;
    private final Location geoLocation;

    // Default Constructor with lat/long
    public RetrieveRestaurantDataAsyncTask(IRestaurantResultCallback callback, Location location) {
        this(callback, location, "");
    }

    // Default Constructor with location keyword
    public RetrieveRestaurantDataAsyncTask(IRestaurantResultCallback callback, String location) {
        this(callback, location, "");
    }

    // Optional
    public RetrieveRestaurantDataAsyncTask(IRestaurantResultCallback callback, Location location, String keyword) {
        this.callback = callback;
        this.keyword = keyword;

        // Note: location or longitude / latitude is mandatory for GET call to work
        this.location = "";
        this.geoLocation = location;
    }

    // Optional
    public RetrieveRestaurantDataAsyncTask(IRestaurantResultCallback callback, String location, String keyword) {
        this.callback = callback;
        this.keyword = keyword;

        // Note: location or longitude / latitude is mandatory for GET call to work
        this.location = location;
        this.geoLocation = null;
    }

    @Override
    protected Response doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        Request request = constructRequest(GlobalConfig.YELP_FETCH_BUSINESSES_SEARCH_URL);

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                callback.onFailure(getOnFailureMessage(response));
                throw new IOException("Unexpected code " + response);
            }

        } catch (IOException e) {
            callback.onFailure(String.format("Failed to call GET request: %s", request.url().toString()));
            e.printStackTrace();
        }
        return response;
    }

    private String getOnFailureMessage(Response response) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Status code ").append(response.code()).append("response: ").append(response.message().toString()).toString();
    }

    @Override
    protected void onPostExecute(Response result) {
        super.onPostExecute(result);
        callback.onSuccess(result);
    }

    private Request constructRequest(String url) {
        final String HEADER_KEY = GlobalConfig.YELP_API_HEADER_KEY;
        final String HEADER_VALUE = GlobalConfig.YELP_API_HEADER_VALUE;

        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();

        if (geoLocation != null) {
            Log.d(getClass().getName(), String.format ("CurrentLocation based search: latitude: %s, longitude: %s", this.geoLocation.getLatitude(), this.geoLocation.getLongitude()));
            httpBuilder.addQueryParameter("latitude", new Double(this.geoLocation.getLatitude()).toString()). addQueryParameter("longitude", new Double(this.geoLocation.getLongitude()).toString());
        } else {
            Log.d(getClass().getName(), String.format ("SearchLocation based search: %s", location));
            httpBuilder.addQueryParameter("location", this.location);
        }

        if (!this.keyword.isEmpty()) {
            httpBuilder.addQueryParameter("term", this.keyword);
        }

        // TODO LN change this to an optional param
        // Default: limit to 10
        httpBuilder.addQueryParameter("limit", new Integer(10).toString());

        return new Request.Builder().url(httpBuilder.build()).header(HEADER_KEY, HEADER_VALUE).get().build();
    }
}
