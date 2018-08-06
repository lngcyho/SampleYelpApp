package com.lchng.ripple.sampleyelpapp.restaurantDetails.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lchng.ripple.sampleyelpapp.R;
import com.lchng.ripple.sampleyelpapp.restaurantDetails.model.RestaurantReview;
import com.squareup.picasso.Picasso;

public class RestaurantReviewView extends LinearLayout {
    private String profileName;
    private String profileImageUrl;
    private String reviewRating;
    private String reviewComment;

    private TextView profileNameView;
    private ImageView profileImageView;
    private TextView ratingView;
    private TextView commentView;

    private Context context;

    public RestaurantReviewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.view_restaurant_review_component, this);

        profileNameView = view.findViewById(R.id.restaurant_review_profile_name_text_view);
        profileImageView = view.findViewById(R.id.restaurant_review_profile_image_view);
        ratingView = view.findViewById(R.id.restaurant_review_rating_text_view);
        commentView = view.findViewById(R.id.restaurant_review_comment_text_view);

        //attrs
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ActionBar);
        try {
            // If we want to manually passed in the value
            profileName = array.getString(R.styleable.RestaurantReviewView_profileName);
            profileImageUrl = array.getString(R.styleable.RestaurantReviewView_profileImageUrl);
            reviewRating = array.getString(R.styleable.RestaurantReviewView_reviewRating);
            reviewComment = array.getString(R.styleable.RestaurantReviewView_reviewComment);

            Picasso.get().load(profileImageUrl).into(profileImageView);
            profileNameView.setText(profileName);
            ratingView.setText(context.getString(R.string.restaurant_row_review_count, reviewRating));
            commentView.setText(reviewComment);
        } finally {
            array.recycle();
        }
    }

    public void setRestaurantReview(RestaurantReview restaurantReview) {
        String emptyString = "";
        if (restaurantReview == null) {
            Picasso.get().load(emptyString).into(profileImageView);
            profileNameView.setText(emptyString);
            ratingView.setText(this.context.getString(R.string.restaurant_row_review_count, emptyString));
            commentView.setText(emptyString);
            return;
        }

        Picasso.get().load(restaurantReview.getUser().getProfileImageUrl()).into(profileImageView);
        profileNameView.setText(restaurantReview.getUser().getProfileName());
        ratingView.setText(this.context.getString(R.string.restaurant_row_review_count, restaurantReview.getReviewRating().toString()));
        commentView.setText(restaurantReview.getComment());
    }
}
