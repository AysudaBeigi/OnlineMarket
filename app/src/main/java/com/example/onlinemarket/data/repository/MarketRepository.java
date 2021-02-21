package com.example.onlinemarket.data.repository;


import android.content.Context;
import android.util.Log;

import com.example.onlinemarket.data.model.Attribute;
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.example.onlinemarket.data.remote.retrofit.WooCommerceAPIService;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketRepository {

    private static String TAG="OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;

    public MarketRepository(Context context) {
        mWooCommerceAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(WooCommerceAPIService.class);
    }

    public void fetchLastProducts(productsCallback productsCallback) {
        Log.d(TAG,"MarketRepository : fetchLastProducts");

        mWooCommerceAPIService.getProducts(NetworkParams.getLastProducts()).
                enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> lastProducts = response.body();
                productsCallback.onItemResponse(lastProducts);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void fetchMostVisitedProducts(productsCallback productsCallback) {
        Log.d(TAG,"MarketRepository : fetchMostVisitedProducts");

        mWooCommerceAPIService.getProducts(NetworkParams.getMostVisitedProducts()).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call,
                                           Response<List<Product>> response) {
                        List<Product> mostVisitedProducts = response.body();
                        productsCallback.onItemResponse(mostVisitedProducts);
                    }
                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void fetchPopularProducts( int page ,productsCallback productsCallback) {

        Log.d(TAG,"MarketRepository : fetchPopularProducts");
        mWooCommerceAPIService.getProducts(NetworkParams.getPopularProducts(page)).
                enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {
                List<Product> popularProducts = response.body();
                productsCallback.onItemResponse(popularProducts);
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategories(CategoriesCallback categoriesCallback) {
        mWooCommerceAPIService.getCategories(NetworkParams.getCategories()).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> categories = response.body();
                        categoriesCallback.onItemResponse(categories);
                    }
                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);

                    }
                });
    }

    public void fetchCategoryProduct(int categoryId, productsCallback productsCallback) {
        mWooCommerceAPIService.getProducts(NetworkParams.getCategoryProducts(categoryId)).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call,
                                           Response<List<Product>> response) {
                        List<Product> categoryProducts = response.body();
                        productsCallback.onItemResponse(categoryProducts);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

     public void fetchSubCategories(int parentId, subCategoriesCallback subCategoriesCallback) {
        mWooCommerceAPIService.getCategories(NetworkParams.getSubCategories(parentId)).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> subCategories = response.body();
                        subCategoriesCallback.onItemResponse(subCategories);
                    }
                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }


    public void fetchProduct(int productId, productCallback productCallback) {
        mWooCommerceAPIService.getProduct(productId, NetworkParams.getBaseQuery()).
                enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call,
                                           Response<Product> response) {
                        Product product = response.body();
                        productCallback.onItemResponse(product);
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void fetchAttributes(AttributesCallback callback) {
        mWooCommerceAPIService.getAttributes(NetworkParams.getBaseQuery())
                .enqueue(new Callback<List<Attribute>>() {
                    @Override
                    public void onResponse(Call<List<Attribute>> call,
                                           Response<List<Attribute>> response) {
                        List<Attribute> attributes = response.body();
                        callback.onItemResponse(attributes);
                    }

                    @Override
                    public void onFailure(Call<List<Attribute>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);

                    }

                });
    }



    public void fetchSearchProducts( Map<String, String> query, productsCallback callBacks) {
        mWooCommerceAPIService.getProducts(query).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {
                List<Product> searchItems = response.body();
                callBacks.onItemResponse(searchItems);
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

   /* public void fetchSearchFilteredProducts(String query, productsCallback callBacks) {

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
*/

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



}
