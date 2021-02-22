package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.repository.CategoryRepository;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {
    private CategoryRepository mCategoryRepository;
    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        mCategoryRepository= new CategoryRepository(application);
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return mCategoryRepository.getCategoriesLiveData();
    }
    public void setCategoriesLiveData() {
        mCategoryRepository.setCategoriesLiveData();
    }

}
