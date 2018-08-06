package com.lchng.ripple.sampleyelpapp.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lchng.ripple.sampleyelpapp.GlobalConfig;
import com.lchng.ripple.sampleyelpapp.R;
import com.lchng.ripple.sampleyelpapp.BaseYelpActivity;
import com.lchng.ripple.sampleyelpapp.home.adapters.IOnItemClickListener;
import com.lchng.ripple.sampleyelpapp.home.adapters.RestaurantAdapter;
import com.lchng.ripple.sampleyelpapp.home.model.Restaurant;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.RestaurantDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeYelpActivity extends BaseYelpActivity implements HomeView {

    HomePresenter homePresenter = new HomePresenter(this);

    private RecyclerView restaurantRecyclerView;
    private RecyclerView.Adapter restaurantAdapter;

    private SwipeRefreshLayout restaurantRefreshLayout;

    private TextView genericTextView;

    private FloatingActionButton sortButton;

    private EditText foodCategoryEditText;
    private EditText locationEditText;

    private Button searchButton;

    // TODO LN check for pagination for load more than 10 when user scroll down
    // TODO LN Group results by respective category - via RestaurantCategorizationType
    // O(N) operation to loop and generate categories and placed then in a hashmap structure (<Category, List<String of restaurant name>)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp_home);

        restaurantRecyclerView = findViewById(R.id.restaurant_recycler_view);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        restaurantRecyclerView.setVisibility(View.INVISIBLE);

        restaurantRefreshLayout = findViewById(R.id.restaurant_swipe_refresh);
        restaurantRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePresenter.searchForRestaurants(getSearchLocation(), foodCategoryEditText.getText().toString());
            }
        });

        genericTextView = findViewById(R.id.generic_text_view);
        genericTextView.setVisibility(View.INVISIBLE);

        foodCategoryEditText = findViewById(R.id.food_category_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePresenter.searchForRestaurants(getSearchLocation(), foodCategoryEditText.getText().toString());
            }
        });

        sortButton = findViewById(R.id.sort_button);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePresenter.sortRestaurantList();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
    }

    @Override
    protected void onNetworkConnected() {
        Log.d(getClass().getName(), "onNetworkConnected");
        if (restaurantRecyclerView.getVisibility() != View.VISIBLE) {
            homePresenter.searchForRestaurants("Toronto", foodCategoryEditText.getText().toString());

            showRestaurantListView();
        }
    }

    @Override
    protected void onNetworkDisconnected() {
        Log.d(getClass().getName(), "onNetworkDisconnected");

        showGenericTextMessage(getString(R.string.no_network));
    }

    @Override
    public void setRestaurants(List<Restaurant> restaurants) {
        // Remove spinning from refresh
        restaurantRefreshLayout.setRefreshing(false);
        if (restaurants == null) {
            showGenericTextMessage(getString(R.string.fetch_network_error));
            return;
        }

        if (restaurants.size() == 0) {
            // Display something like there's no restaurant available?
            showGenericTextMessage(getString(R.string.no_restaurants));
        } else {
            // Update list with restaurants
            restaurantAdapter = new RestaurantAdapter(restaurants, new IOnItemClickListener() {
                @Override
                public void onItemClick(Restaurant restaurant) {
                    Intent intent = new Intent(HomeYelpActivity.this, RestaurantDetailsActivity.class);
                    intent.putExtra(GlobalConfig.BUNDLE_KEYWORD_RESTAURANT, restaurant);
                    startActivity(intent);
                }
            });
            restaurantRecyclerView.setAdapter(restaurantAdapter);
            showRestaurantListView();
        }

        restaurantAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRestaurantListView() {
        restaurantRecyclerView.setVisibility(View.VISIBLE);

        genericTextView.setVisibility(View.GONE);
    }

    @Override
    public void showGenericTextMessage(String text) {
        genericTextView.setText(text);
        genericTextView.setVisibility(View.VISIBLE);

        restaurantRecyclerView.setVisibility(View.GONE);
    }

    private String getSearchLocation() {
        return locationEditText.getText().toString();
    }
}
