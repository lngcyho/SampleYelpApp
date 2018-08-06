package com.lchng.ripple.sampleyelpapp.restaurantDetails;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchng.ripple.sampleyelpapp.BaseYelpActivity;
import com.lchng.ripple.sampleyelpapp.GlobalConfig;
import com.lchng.ripple.sampleyelpapp.R;
import com.lchng.ripple.sampleyelpapp.home.model.Restaurant;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.components.RestaurantReviewView;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.model.RestaurantDetails;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.model.RestaurantReview;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantDetailsActivity extends BaseYelpActivity implements RestaurantDetailView {

    private RestaurantDetailsPresenter restaurantDetailsPresenter;

    private ImageView restaurantImage;

    private TextView ratingTextView;
    private TextView reviewCountTextView;
    private TextView priceTextView;
    private TextView categoriesTextView;
    private TextView isCloseTextView;

    private TextView addressTextView;
    private TextView coordinateTextView;

    private TextView getDirectionTextView;
    private TextView callUsTextView;
    private TextView moreInfoTextView;

    private RestaurantReviewView firstRestaurantReviewView;
    private RestaurantReviewView secondRestaurantReviewView;
    private RestaurantReviewView thirdRestaurantReviewView;

    private Button messageTextButton;
    private Button copyLinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        Restaurant restaurantInfo = (Restaurant) bundle.getSerializable(GlobalConfig.BUNDLE_KEYWORD_RESTAURANT);

        if (restaurantInfo == null) {
            // Something wrong with package?
            return;
        }

        restaurantDetailsPresenter = new RestaurantDetailsPresenter(this);

        setContentView(R.layout.activity_yelp_restaurant_details);
        setTitle(restaurantInfo.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        restaurantImage = findViewById(R.id.restaurant_details_image);

        wireUpRestaurantDetailDescriptionComponent();
        wireUpRestaurantDetailAddressInfoComponent();
        wireUpRestaurantDetailActionWidgetsComponent();
        wireUpRestaurantDetailsReviewComponent();
        wireUpRestaurantDetailsOtherButtonsComponent();

        restaurantDetailsPresenter.fetchRestaurantInfo(restaurantInfo);
    }

    @Override
    protected void onNetworkConnected() {
        // Does nothing
    }

    @Override
    protected void onNetworkDisconnected() {
        // Does nothing
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        restaurantDetailsPresenter.onDestroy();
    }

    private void wireUpRestaurantDetailDescriptionComponent() {
        ratingTextView = findViewById(R.id.restaurant_details_rating);
        reviewCountTextView = findViewById(R.id.restaurant_details_review_count);
        priceTextView = findViewById(R.id.restaurant_details_price);
        categoriesTextView = findViewById(R.id.restaurant_details_categories);
        isCloseTextView = findViewById(R.id.restaurant_details_closed);
    }

    private void wireUpRestaurantDetailAddressInfoComponent() {
        addressTextView = findViewById(R.id.restaurant_details_address);
        coordinateTextView = findViewById(R.id.restaurant_details_coordinates);
    }

    private void wireUpRestaurantDetailActionWidgetsComponent() {
        getDirectionTextView = findViewById(R.id.restaurant_detail_get_direction);
        getDirectionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do Something
            }
        });

        callUsTextView = findViewById(R.id.restaurant_detail_call_us);
        callUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do Something
            }
        });

        moreInfoTextView = findViewById(R.id.restaurant_detail_more_info);
        moreInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do Something
            }
        });
    }

    private void wireUpRestaurantDetailsReviewComponent() {
        firstRestaurantReviewView = findViewById(R.id.restaurant_details_review_1);
        secondRestaurantReviewView = findViewById(R.id.restaurant_details_review_2);
        thirdRestaurantReviewView = findViewById(R.id.restaurant_details_review_3);
    }

    private void wireUpRestaurantDetailsOtherButtonsComponent() {
        messageTextButton = findViewById(R.id.restaurant_message_me);
        messageTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do Something
            }
        });

        copyLinkButton = findViewById(R.id.restaurant_copy_link);
        copyLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do Something
            }
        });

    }

    @Override
    public void setRestaurantImage(String imageUrl) {
        Picasso.get().load(imageUrl).into(restaurantImage);
    }

    @Override
    public void setRestaurantDetailDescription(RestaurantDetails restaurantDetails) {
        ratingTextView.setText(getString(R.string.restaurant_row_rating, restaurantDetailsPresenter.getRating(restaurantDetails)));
        reviewCountTextView.setText(getString(R.string.restaurant_row_review_count, restaurantDetailsPresenter.getReviewCount(restaurantDetails)));
        priceTextView.setText(restaurantDetails.getPriceRange());
        categoriesTextView.setText(restaurantDetailsPresenter.getCategories(restaurantDetails));
        isCloseTextView.setText(restaurantDetailsPresenter.getRestaurantStatus(restaurantDetails));
    }

    @Override
    public void setRestaurantDetailAddressInfo(RestaurantDetails restaurantDetails) {
        addressTextView.setText(restaurantDetailsPresenter.getAddress(restaurantDetails));
        coordinateTextView.setText(restaurantDetailsPresenter.getCoordinates(restaurantDetails));
    }

    @Override
    public void setRestaurantDetailPhoneNumber(String phone) {
        callUsTextView.setText(getString(R.string.call_us, phone));
    }

    @Override
    public void setReviews(List<RestaurantReview> restaurantReviews) {
        RestaurantReview firstReview = restaurantReviews.get(0);
        firstRestaurantReviewView.setRestaurantReview(firstReview != null ? firstReview :  null);

        RestaurantReview secondReview = restaurantReviews.get(1);
        secondRestaurantReviewView.setRestaurantReview(secondReview != null ? secondReview :  null);

        RestaurantReview thirdReview = restaurantReviews.get(2);
        thirdRestaurantReviewView.setRestaurantReview(thirdReview != null ? thirdReview :  null);
    }
}
