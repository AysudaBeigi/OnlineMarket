package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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
        mCategoryRepository = CategoryRepository.getInstance(application);
        mProductRepository = ProductRepository.getInstance(application);
    }


    public LiveData<List<Category>> getParentCategoriesLiveData() {
        return mCategoryRepository.getParentCategoriesLiveData();

    }


    public void setDigitalSubCategoriesLiveData(int parentId) {
        mCategoryRepository.setDigitalSubCategoriesLiveData(parentId);
    }

    public LiveData<List<Category>>
    getDigitalSubCategoriesLiveData() {
        return mCategoryRepository.getDigitalSubCategoriesLiveData();
    }


    public void setSupermarketSubCategoriesLiveData(int parentId) {
        mCategoryRepository.setSupermarketSubCategoriesLiveData(parentId);
    }

    public LiveData<List<Category>>
    getSupermarketSubCategoriesLiveData() {
        return mCategoryRepository.getSupermarketSubCategoriesLiveData();
    }

    //
    public void setBookAndArtSubCategoriesLiveData(int parentId) {
        mCategoryRepository.setBookAndArtSubCategoriesLiveData(parentId);
    }

    public LiveData<List<Category>>
    getBookAndArtSubCategoriesLiveData() {
        return mCategoryRepository.getBookAndArtSubCategoriesLiveData();
    }

    public void mFashionAndClothingSubCategoriesLiveData(int parentId) {
        mCategoryRepository.mFashionAndClothingSubCategoriesLiveData(parentId);
    }

    public LiveData<List<Category>>
    getFashionAndClothingSubCategoriesLiveData() {
        return mCategoryRepository.getFashionAndClothingSubCategoriesLiveData();
    }

    public void setHealthProductsLiveData(int categoryId) {
        mProductRepository.setHealthProductsLiveData(categoryId);
    }

    public LiveData<List<Product>>
    getHealthProductsLiveData() {
        return mProductRepository.getHealthProductsLiveData();
    }

    public void setSpecialSaleProductsLiveData(int categoryId) {
        mProductRepository.setSpecialSaleProductsLiveData(categoryId);
    }

    public LiveData<List<Product>>
    getSpecialSaleProductsLiveData() {
        return mProductRepository.getSpecialSaleProductsLiveData();
    }


    public void setCategoryProductsLiveData(int categoryId) {
        mProductRepository.setCategoryProductsLiveData(categoryId);
    }

    public LiveData<List<Product>> getCategoryProductsLiveData() {
        return mProductRepository.getCategoryProductsLiveData();
    }


    public void setUserSelectedProduct(Product product) {
        mProductRepository.setUserSelectedProduct(product);
    }

    public void setUserSelectedCategory(Category userSelectedCategory) {
        mCategoryRepository.setUserSelectedCategory(userSelectedCategory);
    }

}
