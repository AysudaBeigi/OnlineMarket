package com.example.onlinemarket.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.example.onlinemarket.data.remote.retrofit.WooCommerceAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    public static final int FASHION_ID = 5;
    public static final int DIGITAL_ID = 1;
    public static final int SUPERMARKET_ID = 2;
    public static final int BOOK_ART_ID = 4;
    private static String TAG = "OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;

    private MutableLiveData<List<Category>> mDigitalSubCategoriesLiveData;
    private MutableLiveData<List<Category>> mSupermarketSubCategoriesLiveData;
    private MutableLiveData<List<Category>> mBookAndArtSubCategoriesLiveData;
    private MutableLiveData<List<Category>> mFashionAndClothingSubCategoriesLiveData;
    private MutableLiveData<List<Category>> mParentCategoriesLiveData;


    private Category mUserSelectedCategory;
    private int mUserSelectedCategoryId;
    private static CategoryRepository sInstance;
    private Context mContext;


    public static CategoryRepository getInstance(Context context) {

        if (sInstance == null)
            sInstance = new CategoryRepository(context);
        return sInstance;
    }

    private CategoryRepository(Context context) {
        mContext = context.getApplicationContext();
        mParentCategoriesLiveData = new MutableLiveData<>();
        mFashionAndClothingSubCategoriesLiveData = new MutableLiveData<>();
        mDigitalSubCategoriesLiveData = new MutableLiveData<>();
        mSupermarketSubCategoriesLiveData = new MutableLiveData<>();
        mBookAndArtSubCategoriesLiveData = new MutableLiveData<>();
        mWooCommerceAPIService = RetrofitInstance.getInstance(mContext).getRetrofit().
                create(WooCommerceAPIService.class);

    }

    public MutableLiveData<List<Category>> getParentCategoriesLiveData() {
        return mParentCategoriesLiveData;
    }

    public MutableLiveData<List<Category>> getFashionAndClothingSubCategoriesLiveData() {
        return mFashionAndClothingSubCategoriesLiveData;
    }


    public MutableLiveData<List<Category>> getDigitalSubCategoriesLiveData() {
        return mDigitalSubCategoriesLiveData;
    }

    public MutableLiveData<List<Category>> getSupermarketSubCategoriesLiveData() {
        return mSupermarketSubCategoriesLiveData;
    }



    public MutableLiveData<List<Category>> getBookAndArtSubCategoriesLiveData() {
        return mBookAndArtSubCategoriesLiveData;
    }

    public void setParentCategoriesLiveData() {
        mWooCommerceAPIService.getCategories(NetworkParams.getCategories()).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> categories = response.body();
                        List<Category> parentCategories = new ArrayList<>();
                        for (int i = 0; i < categories.size(); i++) {
                            Log.d(TAG, "onResponse: " + response.body().get(i).toString());
                            if (response.body().get(i).getParent() == 0) {
                                parentCategories.add(response.body().get(i));
                            }

                        }
                        mParentCategoriesLiveData.setValue(parentCategories);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);

                    }
                });
    }


    public void mFashionAndClothingSubCategoriesLiveData() {
        mWooCommerceAPIService.getCategories(NetworkParams.getSubCategories(FASHION_ID)).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> subCategories = response.body();
                        mFashionAndClothingSubCategoriesLiveData.setValue(subCategories);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void setDigitalSubCategoriesLiveData() {
        mWooCommerceAPIService.getCategories(NetworkParams.getSubCategories(DIGITAL_ID)).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> subCategories = response.body();
                        mDigitalSubCategoriesLiveData.setValue(subCategories);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void setSupermarketSubCategoriesLiveData() {
        mWooCommerceAPIService.getCategories(NetworkParams.getSubCategories(SUPERMARKET_ID)).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> subCategories = response.body();
                        mSupermarketSubCategoriesLiveData.setValue(subCategories);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void setBookAndArtSubCategoriesLiveData() {
        mWooCommerceAPIService.getCategories(NetworkParams.getSubCategories(BOOK_ART_ID)).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        List<Category> subCategories = response.body();
                        mBookAndArtSubCategoriesLiveData.setValue(subCategories);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }


    public void setUserSelectedCategory(Category userSelectedCategory) {
        mUserSelectedCategory = userSelectedCategory;
    }

    public Category getUserSelectedCategory() {
        return mUserSelectedCategory;
    }

    public int getUserSelectedCategoryId() {
        return mUserSelectedCategoryId;
    }

    public void setUserSelectedCategoryId(int userSelectedCategoryId) {
        mUserSelectedCategoryId = userSelectedCategoryId;
    }

}
