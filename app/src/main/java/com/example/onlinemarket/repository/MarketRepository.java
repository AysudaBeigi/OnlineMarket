package com.example.onlinemarket.repository;


import android.content.Context;
import android.util.Log;


import com.example.onlinemarket.model.Attribute;
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

    public void fetchAllProductItemsAsync(productsCallback callBacks) {

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

    public void fetchLastProducts(int page, productsCallback callBacks) {
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


    public void fetchMostVisitedProducts(int page, productsCallback callBacks) {
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

    public void fetchPopularProducts(int page, productsCallback callBacks) {
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

    public void fetchCategories(int page, CategoriesCallback callBacks) {
        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);
        insideMap.put("page", String.valueOf(page));
        insideMap.put("per_page", String.valueOf(10));
        insideMap.put("parent", String.valueOf(0));

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

    public void fetchCategoryProduct(int page, int id, productsCallback callBacks) {
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
                                            String orderBy, productsCallback callBacks) {
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
                        List<Product> products = response.body();
                        callBacks.onItemResponse(products);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }
    public void fetchProductsByOrder(int page,String orderBy, productsCallback callBacks) {
        HashMap<String, String> localMap = new HashMap<>();

        localMap.putAll(BASE_KEYS);
        localMap.put("page", String.valueOf(page));
        localMap.put("orderby", orderBy);

        mRequestService.getProducts(localMap).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call,
                                           Response<List<Product>> response) {
                        List<Product> products = response.body();
                        callBacks.onItemResponse(products);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void fetchSubCategories(int parentId, subCategoriesCallback callback) {
        HashMap<String, String> localMap = new HashMap<>();

        localMap.putAll(BASE_KEYS);
        localMap.put("parent", String.valueOf(parentId));

        mRequestService.getCategories(localMap).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> categoryItems = response.body();
                        //update adapter of recyclerview
                        callback.onItemResponse(categoryItems);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });


    }


    public void fetchProduct(int id, productCallback callBacks) {
        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);

        mRequestService.getProduct(id, insideMap).
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

    public void fetchAttributes(AttributesCallback callback) {
        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);

        mRequestService.getAttributes(insideMap)
                .enqueue(new Callback<List<Attribute>>() {
                    @Override
                    public void onResponse(Call<List<Attribute>> call,
                                           Response<List<Attribute>> response) {
                        List<Attribute> attributes = response.body();
                        //update adapter of recyclerview
                        callback.onItemResponse(attributes);
                    }

                    @Override
                    public void onFailure(Call<List<Attribute>> call, Throwable t) {
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

    public void fetchSearchProducts(String query, productsCallback callBacks) {

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

    public void fetchSearchFilteredProducts(String query, productsCallback callBacks) {

        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);
        insideMap.put("search", query);
        insideMap.put("attribute", "pa_color");
        insideMap.put("attribute_term", colorId);

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


    public interface productsCallback {
        void onItemResponse(List<Product> products);
    }

    public interface CategoriesCallback {
        void onItemResponse(List<Category> categories);
    }

    public interface subCategoriesCallback {
        void onItemResponse(List<Category> subCategories);
    }

    public interface productCallback {
        void onItemResponse(Product product);
    }

    public interface AttributesCallback {
        void onItemResponse(List<Attribute> attributes);
    }

   /* public interface CustomerCallbacks {
        void onItemResponse(Customer createCustomer);
    }
*/

}
