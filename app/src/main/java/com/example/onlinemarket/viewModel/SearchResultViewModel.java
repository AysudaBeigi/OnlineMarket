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

public class SearchResultViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;

    private SearchResultViewModel(@NonNull Application application, Context context) {
        super(application);
        mProductRepository = new ProductRepository(context);

    }

    public void setSearchResultProductsLiveData(Map<String, String> query) {
        mProductRepository.setSearchResultProductsLiveData(query);
    }
    public MutableLiveData<List<Product>> getSearchResultProductsLiveData() {
        return mProductRepository.getSearchResultProductsLiveData();
    }

}
