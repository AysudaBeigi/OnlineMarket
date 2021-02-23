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
import java.util.Map;

public class SubCategoryProductsViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;

    private SubCategoryProductsViewModel(@NonNull Application application, Context context) {
        super(application);
        mProductRepository =  ProductRepository.getInstance(context);
        mCategoryRepository=CategoryRepository.getInstance(application);
    }



    public MutableLiveData<List<Product>> getCategoryProductsLiveData() {
        return mProductRepository.getCategoryProductsLiveData();
    }
    public Category getUserSelectedCategory() {
        return mCategoryRepository.getUserSelectedCategory();
    }
    public void setUserSelectedProduct(Product product){

        mProductRepository.setUserSelectedProduct(product);
    }
    public void setSearchResultProductsLiveData(Map<String, String> query) {
        mProductRepository.setSearchResultProductsLiveData(query);
    }


}
