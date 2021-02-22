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

public class CategoriesViewModel extends AndroidViewModel {
    private CategoryRepository mCategoryRepository;
    private ProductRepository mProductRepository;
    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        mCategoryRepository= new CategoryRepository(application);
        mProductRepository=ProductRepository.getInstance(application);
    }

    public void setCategoriesLiveData() {
        mCategoryRepository.setCategoriesLiveData();
    }
    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return mCategoryRepository.getCategoriesLiveData();
    }

    public void setSubCategoriesLiveData(int parentId) {
        mCategoryRepository.setSubCategoriesLiveData(parentId);
    }
    public MutableLiveData<List<Category>> getSubCategoriesLiveData() {
        return mCategoryRepository.getSubCategoriesLiveData();
    }

    public void setCategoryProductsLiveData(int categoryId){
        mProductRepository.setCategoryProductsLiveData(categoryId);
    }
    public MutableLiveData<List<Product>> getCategoryProductsLiveData() {
        return mProductRepository.getCategoryProductsLiveData();
    }

}
