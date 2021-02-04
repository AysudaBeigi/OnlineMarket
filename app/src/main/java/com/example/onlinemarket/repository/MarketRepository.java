package com.example.onlinemarket.repository;


import android.content.Context;
import android.util.Log;


import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.network.MarketService;
import com.example.onlinemarket.retrofit.RetrofitInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.onlinemarket.network.NetworkParams.CONSUMER_KEY;
import static com.example.onlinemarket.network.NetworkParams.CONSUMER_SECRET;

public class MarketRepository {

    private final String TAG = "Repository";
    private Context mContext;

    private List<Product> mProducts;
    private MarketService mRequestService;

    public static final Map<String, String> BASE_KEYS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);

    }};

    public List<Product> getProducts() {
        return mProducts;
    }


    public MarketRepository(Context context) {
        mRequestService = RetrofitInstance.getInstance(context).
                create(MarketService.class);
        mContext = context;
    }

    public void fetchAllProductItemsAsync(Callbacks callBacks) {

        mRequestService.getProducts(BASE_KEYS).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(items);
            }


            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

            }
        });
    }

    public void fetchRecentProducts(int page, Callbacks callBacks) {
        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);
        insideMap.put("page", String.valueOf(page));
        insideMap.put("orderby", "date");

        mRequestService.getProducts(insideMap).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> recentItems = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(recentItems);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void fetchMostVisitedProducts(int page, Callbacks callBacks) {
        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);
        insideMap.put("page", String.valueOf(page));
        insideMap.put("orderby", "rating");

        mRequestService.getProducts(insideMap).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call,
                                           Response<List<Product>> response) {

                        List<Product> mostVisitedItems = response.body();
                        //update adapter of recyclerview
                        callBacks.onItemResponse(mostVisitedItems);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void fetchRatedProducts(int page, Callbacks callBacks) {
        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);
        insideMap.put("page", String.valueOf(page));
        insideMap.put("orderby", "popularity");

        mRequestService.getProducts(insideMap).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {

                List<Product> ratedItems = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(ratedItems);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategory(int page, CategoryCallbacks callBacks) {
        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);
        insideMap.put("page", String.valueOf(page));
        insideMap.put("per_page", String.valueOf(10));

        mRequestService.getCategories(insideMap).
                enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call,
                                   Response<List<Category>> response) {
                List<Category> categoriesItems = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(categoriesItems);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

            }
        });
    }

    public void fetchCategoryProduct(int page, int id, Callbacks callBacks) {
        HashMap<String, String> localMap = new HashMap<>();

        localMap.putAll(BASE_KEYS);
        localMap.put("page", String.valueOf(page));
        localMap.put("category", String.valueOf(id));

        mRequestService.getProducts(localMap).
                enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {

                List<Product> categoryItems = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(categoryItems);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategoryProductByOrder(int page, int id,
                                            String orderBy, Callbacks callBacks) {
        HashMap<String, String> localMap = new HashMap<>();

        localMap.putAll(BASE_KEYS);
        localMap.put("page", String.valueOf(page));
        localMap.put("category", String.valueOf(id));
        localMap.put("orderby", orderBy);

        mRequestService.getProducts(localMap).
                enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {
                List<Product> categoryItems = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(categoryItems);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void fetchSingleProduct(int id, SingleCallbacks callBacks) {
        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);

        mRequestService.getSingleProduct(id, insideMap).
                enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call,
                                           Response<Product> response) {

                        Product product = response.body();
                        //update adapter of recyclerview
                        callBacks.onItemResponse(product);
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

   /* public void sendCustomer(String email, CustomerCallbacks customerCallbacks) {

        mRequestService.createCustomer(email).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Log.d(TAG, "onResponse: " + response.body());
                Log.d(TAG, "onResponse: " + response.code());

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

    }
*/

    public void fetchSearchProducts(String query, Callbacks callBacks) {

        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);
        insideMap.put("search", query);

        mRequestService.getProducts(insideMap).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {
                List<Product> searchItems = response.body();

                //update adapter of recyclerview
                callBacks.onItemResponse(searchItems);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public interface Callbacks {
        void onItemResponse(List<Product> items);
    }

    public interface CategoryCallbacks {
        void onItemResponse(List<Category> items);
    }

    public interface SingleCallbacks {
        void onItemResponse(Product item);
    }

   /* public interface CustomerCallbacks {
        void onItemResponse(Customer createCustomer);
    }
*/

}
