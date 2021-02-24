package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CategoryRepository;
import com.example.onlinemarket.data.repository.ProductRepository;

import java.util.List;
import java.util.Map;

public class SubCategoryProductsViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;

    public SubCategoryProductsViewModel(@NonNull Application application) {
        super(application);
        mProductRepository = ProductRepository.getInstance(application);
        mCategoryRepository = CategoryRepository.getInstance(application);
    }


    public MutableLiveData<List<Product>> getCategoryProductsLiveData() {
        return mProductRepository.getCategoryProductsLiveData();
    }


    public Category getUserSelectedCategory() {
        return mCategoryRepository.getUserSelectedCategory();
    }

    public void setUserSelectedProduct(Product product) {

        mProductRepository.setUserSelectedProduct(product);
    }

    public void setSearchResultProductsLiveData(Map<String, String> query) {
        mProductRepository.setSearchResultProductsLiveData(query);
    }


}
