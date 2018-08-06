package com.lchng.ripple.sampleyelpapp.home.adapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchng.ripple.sampleyelpapp.R;
import com.lchng.ripple.sampleyelpapp.home.model.Restaurant;
import com.lchng.ripple.sampleyelpapp.home.model.RestaurantCategory;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final List<Restaurant> listOfRestaurnts;
    private final IOnItemClickListener listener;

    public RestaurantAdapter(List<Restaurant> restaurants, IOnItemClickListener listener) {
        this.listOfRestaurnts = restaurants;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_restaurant_view_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listOfRestaurnts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listOfRestaurnts.size();
    }

    // Layout for individual entries
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView rating;
        private TextView reviewCount;
        private TextView location;
        private TextView price;
        private TextView categories;
        private ImageView image;


        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.restaurant_title);
            rating = view.findViewById(R.id.restaurant_rating);
            reviewCount = view.findViewById(R.id.restaurant_review_count);
            location = view.findViewById(R.id.restaurant_location);
            price = view.findViewById(R.id.restaurant_price);
            categories = view.findViewById(R.id.restaurant_categories);
            image = view.findViewById(R.id.restaurant_image);

        }

        public void bind(final Restaurant restaurant, final IOnItemClickListener listener) {
            title.setText(restaurant.getName());
            rating.setText(itemView.getContext().getString(R.string.restaurant_row_rating, restaurant.getRating().toString()));
            reviewCount.setText(itemView.getContext().getString(R.string.restaurant_row_review_count, restaurant.getReviewCount().toString()));
            location.setText(getLocation(restaurant.getLocation()));
            price.setText(restaurant.getPriceRange());
            categories.setText(getCategoriesString(restaurant.getCategories()));
            Picasso.get().load(restaurant.getImageUrl()).into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                listener.onItemClick(restaurant);
                }
            });
        }

        private String getLocation(Map<String, Object> location) {
            StringBuilder sb = new StringBuilder();
            sb.append((String) location.get("address1")).append((String) location.get("city"));
            return sb.toString();
        }

        private String getCategoriesString(List<RestaurantCategory> categories) {
            if (categories == null) {
                return "";
            }

            StringBuilder sb = new StringBuilder();
            for (RestaurantCategory category : categories) {
                sb.append(category.getTitle()).append(", ");
            }
            sb.setLength(sb.length() - 2);
            return sb.toString();
        }
    }
}
