package com.example.onlinemarket.data.remote;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_f025265e3479f7bee8e93bffe5685517b93ec27d";
    public static final String CONSUMER_SECRET = "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0";
    public static final String DESC = "desc";
    public static final String PARENT_CATEGORY = "0";
    public static final String PARENT = "parent";
    public static final String ORDER = "order";
    public static final String CATEGORY = "category";
    public static final String SEARCH = "search";
    public static final String ORDERBY = "orderby";
    public static final String PAGE = "page";
    public static final String PRODUCT_ID = "product_id";
    public static final String DATE = "date";
    public static final String RATING = "rating";
    public static final String POPULARITY = "popularity";
    public static final String PER_PAGE = "per_page";
    public static final int SPECIAL_PROUCT_ID = 608;

    public static String TAG = "OnlineMarket";

    public static final Map<String, String> BASE_OPTIONS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};

    public static Map<String, String> getMostVisitedProducts() {
        Log.d(TAG, "MarketRepository : getMostVisitedProducts");

        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PAGE, String.valueOf(1));
        queryMap.put(ORDERBY, RATING);
        return queryMap;
    }


    public static Map<String, String> getPopularProducts() {
        Log.d(TAG, "MarketRepository : getPopularProducts");

        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PAGE, String.valueOf(1));
        queryMap.put(ORDERBY, POPULARITY);
        return queryMap;
    }


    public static Map<String, String> getAmazingOfferProducts() {
        Log.d(TAG, "MarketRepository : getPopularProducts");

        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PAGE, String.valueOf(2));
        queryMap.put(ORDERBY, POPULARITY);

        return queryMap;
    }


    public static Map<String, String> getLastProducts() {
        Log.d(TAG, "MarketRepository : getLastProducts");

        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PAGE, String.valueOf(1));
        queryMap.put(ORDERBY, DATE);
        queryMap.put(ORDER, DESC);

        return queryMap;
    }

    public static Map<String, String> getCategoryProducts(int categoryId) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PAGE, String.valueOf(1));
        queryMap.put(CATEGORY, String.valueOf(categoryId));
        return queryMap;

    }

    public static Map<String, String> getBaseQuery() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        return queryMap;
    }


    public static Map<String, String> getCategories() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PAGE, String.valueOf(1));
        queryMap.put(PER_PAGE, String.valueOf(10));
        queryMap.put(PARENT, PARENT_CATEGORY);

        return queryMap;
    }

    public static Map<String, String> getSubCategories(int parentId) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PARENT, String.valueOf(parentId));
        return queryMap;
    }

    public static Map<String, String> getSearchAllProducts(String query) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(SEARCH, query);
        return queryMap;
    }

    public static Map<String, String> getSearchCategoryProducts(String query, int categoryId) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(SEARCH, query);
        queryMap.put(CATEGORY, String.valueOf(categoryId));
        return queryMap;
    }


    public static Map<String, String>
    getOrderedSearchResultProducts(Map<String ,String > queryMap, String orderby) {
        queryMap.put(ORDERBY, orderby);
        if (orderby.equals("price_asc"))
            queryMap.put(ORDER, "asc");
        return queryMap;
    }



    public static Map<String, String> getFilterSearchProducts(String query, String colorId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);
        products.put("attribute", "pa_color");
        products.put("attribute_term", colorId);

        return products;
    }

    public static Map<String, String> getCustomer(String email) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("email", email);
        return queryOptions;
    }


    public static Map<String, String> getProductComments(int productId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(PRODUCT_ID, String.valueOf(productId));
        return products;
    }


    public static Map<String, String> getMainAddress() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);

        return products;
    }


}
