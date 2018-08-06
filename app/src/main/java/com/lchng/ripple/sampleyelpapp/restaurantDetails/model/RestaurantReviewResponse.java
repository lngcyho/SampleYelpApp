package com.lchng.ripple.sampleyelpapp.restaurantDetails.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Model that is convert from HTTP JSON call (Fetch Restaurant Reviews call)
 */
public class RestaurantReviewResponse implements Serializable {
    private List<RestaurantReview> reviews;

    @SerializedName("total")
    private Integer totalReviews;

    @SerializedName("possible_languages")
    private List<String> languages;

    public List<RestaurantReview> getRestaurantReviews() {
        return reviews;
    }

    public Integer getTotalNumberOfReviews() {
        return totalReviews;
    }

    public List<String> getSupportedLanguages() {
        return languages;
    }
}
