package com.lchng.ripple.sampleyelpapp.home;

import com.lchng.ripple.sampleyelpapp.home.model.Restaurant;

import java.util.List;

public interface HomeView {
    void setRestaurants(List<Restaurant> restaurants);
    void showRestaurantListView();
    void showGenericTextMessage(String text);
}
