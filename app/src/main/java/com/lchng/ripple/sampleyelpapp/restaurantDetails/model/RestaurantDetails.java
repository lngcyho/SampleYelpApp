package com.lchng.ripple.sampleyelpapp.restaurantDetails.model;

import com.lchng.ripple.sampleyelpapp.R;
import com.lchng.ripple.sampleyelpapp.home.model.RestaurantCategory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Presentation Model that store everything display on the RestaurantDetail Page
 */
public class RestaurantDetails implements Serializable {
    private String name;
    private String imageUrl;
    private String phone;
    private List<RestaurantCategory> categories;
    private Integer reviewCount;
    private Double rating;
    private Map<String,Object> location;
    private Map<String,Double> coordinates;
    private List<RestaurantReview> restaurantReviews;
    private List<Map<String,Object>> hours;
    private String priceRange;

    public RestaurantDetails setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestaurantDetails setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public RestaurantDetails setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RestaurantDetails setCategories(final List<RestaurantCategory> restaurantCategories) {
        this.categories = restaurantCategories;
        return this;
    }

    public List<RestaurantCategory> getCategories() {
        return categories;
    }

    public RestaurantDetails setReviewCount(final Integer reviewCount) {
        this.reviewCount = reviewCount;
        return this;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public RestaurantDetails setRating (final Double rating) {
        this.rating = rating;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public RestaurantDetails setPriceRange (final String priceRange) {
        this.priceRange = priceRange;
        return this;
    }
    
    public String getPriceRange() {
        return priceRange;
    }
    public RestaurantDetails setLocation(final Map<String, Object> location) {
        this.location = location;
        return this;
    }

    public Map<String, Object> getLocation() {
        return location;
    }

    public RestaurantDetails setCoordinates(final Map<String, Double> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Map<String, Double> getCoordinates() {
        return coordinates;
    }

    public RestaurantDetails setRestaurantReviews(final List<RestaurantReview> restaurantReviews) {
        this.restaurantReviews = restaurantReviews;
        return this;
    }

    public List<RestaurantReview> getRestaurantReviews() {
        return restaurantReviews;
    }

    public RestaurantDetails setHours(List<Map<String, Object>> hours) {
        this.hours = hours;
        return this;
    }

    // Note this returns a R.String value back
    public int getRestaurantStatus() {
        if (this.hours != null && this.hours.size() > 0) {
            for (Map<String, Object> hourSlots : this.hours) {
                Boolean isOpenNow = (Boolean) hourSlots.get("is_open_now");
                if (isOpenNow != null) {
                    return isOpenNow ? R.string.open : R.string.closed;
                }
            }
        }

        return R.string.not_available;
    }
}
