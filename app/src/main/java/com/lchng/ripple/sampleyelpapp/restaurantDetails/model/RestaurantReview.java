package com.lchng.ripple.sampleyelpapp.restaurantDetails.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantReview implements Serializable{
    private String id;

    private String url;

    @SerializedName("text")
    private String comment;

    private Integer rating;

    @SerializedName("time_create")
    private String postedCreated;

    private UserProfile user;

    public String getReviewId() {
        return id;
    }

    public String getReviewUrl() {
        return url;
    }

    public String getComment() {
        return comment;
    }

    public Integer getReviewRating() {
        return rating;
    }

    public String getPostedCreated() {
        return postedCreated;
    }

    public UserProfile getUser() {
        return user;
    }
}
