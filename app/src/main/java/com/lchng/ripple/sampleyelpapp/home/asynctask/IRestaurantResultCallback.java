package com.lchng.ripple.sampleyelpapp.home.asynctask;

import okhttp3.Response;

public interface IRestaurantResultCallback {
    void onSuccess (Response results);
    void onFailure (String error);
}