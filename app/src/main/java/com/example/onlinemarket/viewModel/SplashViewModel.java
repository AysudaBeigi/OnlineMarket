package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.data.repository.CategoryRepository;
import com.example.onlinemarket.data.repository.ProductRepository;
import com.example.onlinemarket.utils.NetworkUtils;

public class SplashViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;


    public SplashViewModel(@NonNull Application application) {

        super(application);
        mProductRepository = ProductRepository.getInstance(application);
        mCategoryRepository =CategoryRepository.getInstance(application);


    }


    public boolean isNotworkConnected(Context context) {
        return NetworkUtils.isNetworkConnected(context);
    }

    public void setSpecialProductLiveData() {
        mProductRepository.setSpecialProductLiveData();
    }

    public void setLatestProductsLiveData() {
        mProductRepository.setLatestProductsLiveData();
    }

    public void setPopularProductsLivData() {
        mProductRepository.setPopularProductsLiveData();
    }

    public void setAmazingOfferProductsLiveData() {
        mProductRepository.setAmazingOfferProductsLiveData();
    }

    public void setMostVisitedProductsLiveData() {
        mProductRepository.setMostVisitedProductsLiveData();
    }

    public void setParentCategoriesLiveData() {
        mCategoryRepository.setParentCategoriesLiveData();
    }


}
