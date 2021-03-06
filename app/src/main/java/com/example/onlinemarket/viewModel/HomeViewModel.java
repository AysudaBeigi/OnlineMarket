package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CategoryRepository;
import com.example.onlinemarket.data.repository.ProductRepository;

import java.util.List;
import java.util.Map;

public class HomeViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;
    private NavController mNavController;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mProductRepository =ProductRepository.getInstance(application);
        mCategoryRepository =CategoryRepository.getInstance(application);

    }


    public LiveData<List<Product>> getLatestProductsLiveData() {
        return mProductRepository.getLatestProductsLiveData();
    }

    public LiveData<List<Product>> getPopularProductsLiveData() {
        return mProductRepository.getPopularProductsLiveData();
    }

    public LiveData<List<Product>> getAmazingOfferProductsLiveData() {
        return mProductRepository.getAmazingOfferProductsLiveData();
    }

    public LiveData<List<Product>> getMostVisitedProductsLiveData() {
        return mProductRepository.getMostVisitedProductsLiveData();
    }


    public LiveData<Product> getSpecialProductLiveData() {
        return mProductRepository.getSpecialProductLiveData();
    }

    public LiveData<List<Category>> getParentCategoriesLiveData() {
        return mCategoryRepository.getParentCategoriesLiveData();
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
        mCategoryRepository.setParentCategoriesLiveData();
    }


    public void setUserSelectedProduct(Product product){

        mProductRepository.setUserSelectedProduct(product);
    }
    public void setSearchResultProductsLiveData(Map<String, String> query) {
        mProductRepository.setSearchResultProductsLiveData(query);
    }


}
