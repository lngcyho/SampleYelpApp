package com.lchng.ripple.sampleyelpapp.restaurantDetails.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserProfile implements Serializable{
    private String id;

    @SerializedName("profile_url")
    private String profileUrl;

    @SerializedName("image_url")
    private String imageUrl;

    private String name;

    public String getProfileId() {
        return id;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getProfileImageUrl() {
        return imageUrl;
    }

    public String getProfileName() {
        return name;
    }
}
