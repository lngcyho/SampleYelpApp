package com.lchng.ripple.sampleyelpapp.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lchng.ripple.sampleyelpapp.home.asynctask.IRestaurantResultCallback;
import com.lchng.ripple.sampleyelpapp.home.asynctask.RetrieveRestaurantDataAsyncTask;
import com.lchng.ripple.sampleyelpapp.home.model.Restaurant;
import com.lchng.ripple.sampleyelpapp.home.model.RestaurantResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomePresenter {
    HomeYelpActivity context;
    private List<Restaurant> restaurants;

    private Boolean isRestaurantListInAlphabetical = false;

    public HomePresenter(HomeYelpActivity context) {
        this.context = context;
    }

    public void onDestroy() {
        context = null;
    }

    public void fetchRestaurantByLocationAndCategory(Location location, String category) {
        RetrieveRestaurantDataAsyncTask retrieveRestaurantDataAsyncTask = new RetrieveRestaurantDataAsyncTask(new IRestaurantResultCallback() {
            @Override
            public void onSuccess(Response results) {
                onRetrieveRestaurantDataAsyncTaskSucessCallback(results);
            }

            @Override
            public void onFailure(String error) {
                onRetrieveRestaurantDataAsyncTaskFailureCallback(error);
            }
        }, location, category);

        retrieveRestaurantDataAsyncTask.execute();
    }

    public void fetchRestaurantByLocationAndCategory(String location, String category) {
        /**
         *  This is where things can get slightly interesting. We have an option of abstracting this into another layout
         *  to either fetch from local storage, caching, make http or determine based on some type of logic. For simplicity, I'll just
         *  fire to make HTTP call
         */

        RetrieveRestaurantDataAsyncTask retrieveRestaurantDataAsyncTask = new RetrieveRestaurantDataAsyncTask(new IRestaurantResultCallback() {
            @Override
            public void onSuccess(Response results) {
                onRetrieveRestaurantDataAsyncTaskSucessCallback(results);
            }

            @Override
            public void onFailure(String error) {
                onRetrieveRestaurantDataAsyncTaskFailureCallback(error);
            }
        }, location, category);

        retrieveRestaurantDataAsyncTask.execute();
    }

    private void onRetrieveRestaurantDataAsyncTaskSucessCallback(Response results) {
        Gson gson = new Gson();

        try (ResponseBody responseBody = results.body()){
            String response = responseBody.string();
            Log.d(getClass().getName(), "onSuccess: " + response);

            RestaurantResponse restaurantResponse = gson.fromJson(response, RestaurantResponse.class);

            restaurants = restaurantResponse.getRestaurants();
            context.setRestaurants(restaurants);
        } catch (IOException e) {
            // Fail to parse data which imply logic error or incorrect data or data structure error
            e.printStackTrace();
            context.setRestaurants(null);
        }
    }

    private void onRetrieveRestaurantDataAsyncTaskFailureCallback(String error) {
        // HTTP response error
        context.setRestaurants(null);
    }

    public void searchForRestaurants(String location, String category) {
        boolean isSearchLocationFieldEmpty = (location == null || location.isEmpty());

        if (isSearchLocationFieldEmpty && context.getLastKnownLocation() == null) {
            // Do nothing if there's no searchLocation or currentLocation because HTTP won't work
            context.setRestaurants(null);
            return;
        }

        // use geolocation is no location is passed in
        if (isSearchLocationFieldEmpty) {
            fetchRestaurantByLocationAndCategory(context.getLastKnownLocation(), category);
        } else {
            fetchRestaurantByLocationAndCategory(location, category);
        }
    }

    public void sortRestaurantList() {
        // Sort by alphabetically for now
        isRestaurantListInAlphabetical =  !isRestaurantListInAlphabetical;

        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return isRestaurantListInAlphabetical ? r1.getName().compareTo(r2.getName()) : r2.getName().compareTo(r1.getName());
            }
        });

        context.setRestaurants(restaurants);
    }
}
