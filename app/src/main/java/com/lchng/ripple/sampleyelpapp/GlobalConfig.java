package com.lchng.ripple.sampleyelpapp;

/**
 *
 */
public class GlobalConfig {
    final static public String YELP_FETCH_BUSINESSES_SEARCH_URL = "https://api.yelp.com/v3/businesses/search";

    final static public String YELP_FETCH_BUSINESS_DETAILS_URL = "https://api.yelp.com/v3/businesses/%s";
    final static public String YELP_FETCH_BUSINESS_DETAIL_REVIEWS_URL = "https://api.yelp.com/v3/businesses/%s/reviews";

    final static private String YELP_API_KEY = "PowqVlA67TQge_BOLMqHHqnNHaqYo6O5_0MnRIxDzJTJuqW4yu0SfvJJB0Ng6D9wTaxt0VLo1z7LI0BlZnrmUd3f38Kq5xwJHO-M4CnXKyKXBHL4QTM_Uah3pAxdW3Yx";
    final static public String YELP_API_HEADER_KEY = "Authorization";
    final static public String YELP_API_HEADER_VALUE = String.format("Bearer %s", GlobalConfig.YELP_API_KEY);

    final static public String BUNDLE_KEYWORD_RESTAURANT = "RESTAURANT";
    final static float LOCATION_REFRESH_DISTANCE = 10; // 10 meters
    final static long LOCATION_REFRESH_TIME = 1000 * 60; // Every minute
}
