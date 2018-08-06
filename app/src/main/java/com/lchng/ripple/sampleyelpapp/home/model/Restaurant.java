package com.lchng.ripple.sampleyelpapp.home.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Restaurant implements Serializable{

    private String id;

    private String name;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("is_closed")
    private boolean isClosed;

    private String url;

    @SerializedName("review_count")
    private Integer reviewCount;

    private List<RestaurantCategory> categories;

    private Double rating;

    private Map<String, Double> coordinates;

    private String price;

    private Map<String, Object> location;

    @SerializedName("display_phone")
    private String phone;

    private Double distance;

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String getUrl() {
        return url;
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

    public Map<String, Double> getCoordinates() {
        return coordinates;
    }

    public String getPriceRange() {
        return price;
    }

    public Map<String, Object> getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    // Constructor for testing ?
    public Restaurant(String name) {
        this.name = name;
    }
}
