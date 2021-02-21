package com.example.onlinemarket.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.example.onlinemarket.data.remote.retrofit.WooCommerceAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private static String TAG="OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;

    public CategoryRepository(Context context) {
        mWooCommerceAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(WooCommerceAPIService.class);
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


    public interface CategoriesCallback {
        void onItemResponse(List<Category> categories);
    }

    public interface subCategoriesCallback {
        void onItemResponse(List<Category> subCategories);
    }




}
