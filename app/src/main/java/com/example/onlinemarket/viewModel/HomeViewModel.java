package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CategoryRepository;
import com.example.onlinemarket.data.repository.ProductRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;

    private HomeViewModel(@NonNull Application application, Context context) {
        super(application);
        mProductRepository = new ProductRepository(context);
        mCategoryRepository = new CategoryRepository(context);
    }


    public MutableLiveData<List<Product>> getLatestProductsLiveData() {
        return mProductRepository.getLatestProductsLiveData();
    }

    public MutableLiveData<List<Product>> getPopularProductsLiveData() {
        return mProductRepository.getPopularProductsLiveData();
    }

    public MutableLiveData<List<Product>> getAmazingOfferProductsLiveData() {
        return mProductRepository.getPopularProductsLiveData();
    }

    public MutableLiveData<List<Product>> getMostVisitedProductsLiveData() {
        return mProductRepository.getMostVisitedProductsLiveData();
    }


    public MutableLiveData<Product> getSpecialProductLiveData() {
        return mProductRepository.getProductLiveData();
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return mCategoryRepository.getCategoriesLiveData();
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

    public void setCategoriesLiveData(){
        mCategoryRepository.setCategoriesLiveData();
    }



}
