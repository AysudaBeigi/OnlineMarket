package com.example.onlinemarket.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.example.onlinemarket.data.remote.retrofit.WooCommerceAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private static String TAG = "OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;
    private MutableLiveData<List<Category>> mCategoriesLiveData;
    private MutableLiveData<List<Category>> mSubCategoriesLiveData;

    public CategoryRepository(Context context) {
        mCategoriesLiveData = new MutableLiveData<>();
        mSubCategoriesLiveData = new MutableLiveData<>();
        mWooCommerceAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(WooCommerceAPIService.class);

    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return mCategoriesLiveData;
    }

    public MutableLiveData<List<Category>> getSubCategoriesLiveData() {
        return mSubCategoriesLiveData;
    }

    public void setCategoriesLiveData() {
        mWooCommerceAPIService.getCategories(NetworkParams.getCategories()).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> categories = response.body();
                        mCategoriesLiveData.setValue(categories);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);

                    }
                });
    }

    public void setSubCategoriesLiveData(int parentId) {
        mWooCommerceAPIService.getCategories(NetworkParams.getSubCategories(parentId)).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> subCategories = response.body();
                        mSubCategoriesLiveData.setValue(subCategories);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }


}
