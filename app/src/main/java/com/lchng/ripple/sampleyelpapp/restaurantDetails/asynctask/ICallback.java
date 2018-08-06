package com.lchng.ripple.sampleyelpapp.restaurantDetails.asynctask;

import okhttp3.Response;

public interface ICallback {
    void onSuccess (Response results);
    void onFailure (String error);
}
