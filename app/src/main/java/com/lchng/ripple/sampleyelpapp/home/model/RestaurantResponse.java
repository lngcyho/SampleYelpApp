package com.lchng.ripple.sampleyelpapp.home.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RestaurantResponse implements Serializable{
    @SerializedName("businesses")
    private List<Restaurant> restaurants;

    private Map<String,Object> region;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Map<String, Object> getRegion() {
        return region;
    }
}
