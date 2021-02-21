package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.ProductRepository;

import java.util.List;
import java.util.Map;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;

    private ProductViewModel(@NonNull Application application, Context context) {
        super(application);
        mProductRepository = new ProductRepository(context);
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

    public MutableLiveData<List<Product>> getCategoryProductsLiveData() {
        return mProductRepository.getCategoryProductsLiveData();
    }

    public MutableLiveData<List<Product>> getSearchResultProductsLiveData() {
        return mProductRepository.getSearchResultProductsLiveData();
    }

    public MutableLiveData<Product> getProductLiveData() {
        return mProductRepository.getProductLiveData();
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

    public void setSearchResultProductsLiveData(Map<String, String> query) {
        mProductRepository.setSearchResultProductsLiveData(query);
    }

    public void setCategoryProductsLiveData(int categoryId) {
        mProductRepository.setCategoryProductsLiveData(categoryId);
    }



}
