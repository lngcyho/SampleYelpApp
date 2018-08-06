package com.lchng.ripple.sampleyelpapp.restaurantDetails.model;

import com.google.gson.annotations.SerializedName;
import com.lchng.ripple.sampleyelpapp.home.model.RestaurantCategory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Model that is convert from HTTP JSON call (Fetch Restaurant Detail call)
 */
public class RestaurantDetailsResponse implements Serializable {

    private String id;

    private String name;

    @SerializedName("image_url")
    private String imageUrl;

    private String url;

    @SerializedName("display_phone")
    private String phone;

    @SerializedName("review_count")
    private Integer reviewCount;

    private List<RestaurantCategory> categories;

    private Double rating;

    private Map<String, Object> location;

    private Map<String, Double> coordinates;

    private List<String> photos;

    private String price;

    private List<Map<String, Object>> hours;

    public String getRestaurantId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public List<RestaurantCategory> getCategories() {
        return categories;
    }

    public Double getRating() {
        return rating;
    }

    public Map<String, Object> getLocation() {
        return location;
    }

    public Map<String, Double> getCoordinates() {
        return coordinates;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getPrice() {
        return price;
    }

    public List<Map<String, Object>> getHours() {
        return hours;
    }
}
