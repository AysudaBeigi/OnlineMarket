package com.example.onlinemarket.network;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {
    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_f025265e3479f7bee8e93bffe5685517b93ec27d";
    public static final String CONSUMER_SECRET = "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0";
    public static final String DESC = "desc";
    public static final String PARENT_CATEGORY = "0";
    public static final String PARENT_OF_CATEGORY = "parent";
    public static final String ORDER = "order";
    public static final String CATEGORY = "category";
    public static final String SEARCH = "search";
    public static final String ORDERBY = "order_by";
    public static final String PAGE = "page";
    public static final String PRODUCT = "product";
    public static final String FORCE = "force";
    public static final String TRUE = "true";
    public static final String DATE = "date";
    public static final String RATING = "rating";
    public static final String POPULARITY = "popularity";
    public static final String PER_PAGE = "per_page";
    public static final String PARENT = "parent";


    public static final Map<String, String> BASE_OPTIONS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};

    public static Map<String, String> getMostVisitedProducts() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PAGE, String.valueOf(1));
        queryMap.put(ORDERBY, RATING);
        queryMap.put(ORDER, DESC);
        return queryMap;
    }

    public static Map<String, String> getPopularProducts(int page) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(ORDER, DESC);
        queryMap.put(PAGE, String.valueOf(page));
        queryMap.put(ORDERBY, POPULARITY);

        return queryMap;
    }

    public static Map<String, String> getLastProducts() {
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

    /*public static Map<String, String> getProductsWithParentId(String parentId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(CATEGORY,parentId);

        return products;
    }
*/
   /* public static Map<String, String> getSpecialProducts(String parentId,String page) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(CATEGORY,parentId);
        products.put(PAGE,page);

        return products;
    }
*/
    public static Map<String, String> getCategories() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PAGE, String.valueOf(1));
        queryMap.put(PER_PAGE, String.valueOf(10));
        queryMap.put(PARENT_OF_CATEGORY, PARENT_CATEGORY);

        return queryMap;
    }

    public static Map<String, String> getSubCategories(int parentId) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(PARENT_OF_CATEGORY, String.valueOf(parentId));
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

    public static Map<String, String> getOrderedSearchAllProducts(String query, String orderby) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(SEARCH, query);
        queryMap.put(ORDERBY, orderby);
        return queryMap;
    }

    public static Map<String, String>
    getOrderedSearchCategoryProducts(String query, int categoryId, String orderby) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.putAll(BASE_OPTIONS);
        queryMap.put(SEARCH, query);
        queryMap.put(CATEGORY, String.valueOf(categoryId));
        queryMap.put(ORDERBY, orderby);
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

    public static Map<String, String> getSortedLowToHighSearchProducts(String query) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);
        products.put("orderby", "price");
        products.put("order", "asc");

        return products;
    }

    public static Map<String, String> getSortedHighToLowSearchProducts(String query) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);
        products.put("orderby", "price");
        products.put("order", "desc");

        return products;
    }

    public static Map<String, String> getSortedTotalSalesSearchProducts(String query) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);
        products.put("orderby", "slug");
        products.put("order", "desc");

        return products;
    }

    public static Map<String, String> getCommentOfProduct(String productId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(PRODUCT, productId);

        return products;
    }


    public static Map<String, String> getMainAddress() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);

        return products;
    }

    public static Map<String, String> deleteCommentOfProduct() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(FORCE, TRUE);

        return products;
    }


}
