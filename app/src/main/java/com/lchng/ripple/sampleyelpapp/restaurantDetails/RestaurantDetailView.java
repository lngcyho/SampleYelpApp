package com.lchng.ripple.sampleyelpapp.restaurantDetails;

import com.lchng.ripple.sampleyelpapp.restaurantDetails.model.RestaurantDetails;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.model.RestaurantReview;

import java.util.List;

public interface RestaurantDetailView {
    void setRestaurantImage(String imageUrl);
    void setRestaurantDetailDescription(RestaurantDetails restaurantDetails);
    void setRestaurantDetailAddressInfo(RestaurantDetails restaurantDetails);
    void setRestaurantDetailPhoneNumber(String phone);
    void setReviews(List<RestaurantReview> restaurantReviews);
}
