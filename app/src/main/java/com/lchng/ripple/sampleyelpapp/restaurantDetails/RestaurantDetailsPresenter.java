package com.lchng.ripple.sampleyelpapp.restaurantDetails;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lchng.ripple.sampleyelpapp.GlobalHelper;
import com.lchng.ripple.sampleyelpapp.R;
import com.lchng.ripple.sampleyelpapp.home.model.Restaurant;
import com.lchng.ripple.sampleyelpapp.home.model.RestaurantCategory;
import com.lchng.ripple.sampleyelpapp.home.model.RestaurantResponse;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.asynctask.FetchRestaurantDetailsCallable;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.asynctask.FetchRestaurantReviewsCallable;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.asynctask.ICallback;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.model.RestaurantDetails;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.model.RestaurantDetailsResponse;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.model.RestaurantReviewResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class RestaurantDetailsPresenter {

    private RestaurantDetailsActivity context;

    public RestaurantDetailsPresenter(RestaurantDetailsActivity context) {
        this.context= context;
    }

    public void onDestroy() {
        context = null;
    }

    public String getCoordinates(RestaurantDetails restaurantDetails) {
        return String.format("%s, %s", restaurantDetails.getCoordinates().get("latitude"), restaurantDetails.getCoordinates().get("longitude"));
    }

    public String getAddress(RestaurantDetails restaurantDetails) {
        List<String> addressList = (List<String>) restaurantDetails.getLocation().get("display_address");
        return TextUtils.join(", ", addressList);
    }

    public String getRating(RestaurantDetails restaurantDetails) {
        return restaurantDetails.getRating().toString();
    }

    public String getReviewCount(RestaurantDetails restaurantDetails) {
        return restaurantDetails.getReviewCount().toString();
    }

    public String getCategories(RestaurantDetails restaurantDetails) {
        List<String> categories  = new ArrayList<>();
        for (RestaurantCategory category : restaurantDetails.getCategories()) {
            categories.add(category.getTitle());
        }
        return TextUtils.join(", ", categories);
    }

    public String getRestaurantStatus(RestaurantDetails restaurantDetails) {
        return context.getString(restaurantDetails.getRestaurantStatus());
    }

    public String getPhoneNumber(String phoneNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(phoneNumber).append(')');
        return sb.toString();
    }

    public void fetchRestaurantInfo(Restaurant restaurantInfo) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Callable<Response>> todoTask = getFetchRestaurantInfoTask(restaurantInfo);

        try {
            List<Future<Response>> answers = executorService.invokeAll(todoTask);

            handleFetchRestaurantInfo(answers, restaurantInfo);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private void handleFetchRestaurantInfo(List<Future<Response>> answers, Restaurant fallBackRestaurantInfo) throws ExecutionException, InterruptedException {
        Response fetchRestaurantDetailsResponse = answers.get(0).get();
        Response fetchRestaurantReviewsResponse = answers.get(1).get();

        RestaurantDetails restaurantDetails = new RestaurantDetails();

        if (fetchRestaurantDetailsResponse == null && fetchRestaurantReviewsResponse == null) {
            Log.d(getClass().getName(), "ExecutorService both response has failed");
            restaurantDetails
                    .setName(fallBackRestaurantInfo.getName())
                    .setImageUrl(fallBackRestaurantInfo.getImageUrl())
                    .setPhone(fallBackRestaurantInfo.getPhone())
                    .setCategories(fallBackRestaurantInfo.getCategories())
                    .setReviewCount(fallBackRestaurantInfo.getReviewCount())
                    .setRating(fallBackRestaurantInfo.getRating())
                    .setPriceRange(fallBackRestaurantInfo.getPriceRange())
                    .setLocation(fallBackRestaurantInfo.getLocation())
                    .setCoordinates(fallBackRestaurantInfo.getCoordinates());

            //Send information back to view
            updateRestaurantDetailActivityUi(restaurantDetails);
            return;
        }

        if (fetchRestaurantDetailsResponse != null) {
            Log.d(getClass().getName(), "ExecutorService Successfully fetch Task #1 complete");
            RestaurantDetailsResponse response = GlobalHelper.convertHttpResponseToModelResponse(fetchRestaurantDetailsResponse, RestaurantDetailsResponse.class);

            restaurantDetails
                    .setName(response.getName())
                    .setImageUrl(response.getImageUrl())
                    .setPhone(response.getPhone())
                    .setCategories(response.getCategories())
                    .setReviewCount(response.getReviewCount())
                    .setRating(response.getRating())
                    .setPriceRange(response.getPrice())
                    .setLocation(response.getLocation())
                    .setCoordinates(response.getCoordinates())
                    .setHours(response.getHours());
        }

        if (fetchRestaurantReviewsResponse != null) {
            Log.d(getClass().getName(), "ExecutorService Successfully fetch Task #2 complete");
            RestaurantReviewResponse response = GlobalHelper.convertHttpResponseToModelResponse(fetchRestaurantReviewsResponse, RestaurantReviewResponse.class);

            restaurantDetails.setRestaurantReviews(response.getRestaurantReviews());
        }

        updateRestaurantDetailActivityUi(restaurantDetails);
        return;
    }

    private List<Callable<Response>> getFetchRestaurantInfoTask(Restaurant restaurantInfo) {
        String restaurantId = restaurantInfo.getId();
        List<Callable<Response>> todoTask = new ArrayList<>();

        todoTask.add(new FetchRestaurantDetailsCallable(restaurantId));

        todoTask.add(new FetchRestaurantReviewsCallable(restaurantId));

        return todoTask;
    }

    public void updateRestaurantDetailActivityUi (RestaurantDetails restaurantDetails) {
        context.setRestaurantImage(restaurantDetails.getImageUrl());
        context.setRestaurantDetailDescription(restaurantDetails);
        context.setRestaurantDetailAddressInfo(restaurantDetails);
        context.setRestaurantDetailPhoneNumber(getPhoneNumber(restaurantDetails.getPhone()));
        context.setReviews(restaurantDetails.getRestaurantReviews());
    }
}
