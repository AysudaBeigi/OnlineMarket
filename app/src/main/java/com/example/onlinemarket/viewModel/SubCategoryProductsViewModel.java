package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.ProductRepository;

import java.util.List;

public class SubCategoryProductsViewModel extends AndroidViewModel {

    private ProductRepository mProductRepository;

    private SubCategoryProductsViewModel(@NonNull Application application, Context context) {
        super(application);
        mProductRepository = new ProductRepository(context);
    }


    public void setCategoryProductsLiveData(int categoryId) {
        mProductRepository.setCategoryProductsLiveData(categoryId);
    }

    public MutableLiveData<List<Product>> getCategoryProductsLiveData() {
        return mProductRepository.getCategoryProductsLiveData();
    }


}
