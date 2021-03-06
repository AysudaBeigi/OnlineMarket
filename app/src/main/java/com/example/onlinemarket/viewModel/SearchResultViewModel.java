package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.repository.CategoryRepository;
import com.example.onlinemarket.data.repository.ProductRepository;
import com.example.onlinemarket.utils.OnlineMarketPreferences;

import java.util.List;
import java.util.Map;

public class SearchResultViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;
    private OnlineMarketPreferences mOnlineMarketPreferences;

    public SearchResultViewModel(@NonNull Application application) {
        super(application);
        mProductRepository = ProductRepository.getInstance(application);
        mCategoryRepository = CategoryRepository.getInstance(application);
        mOnlineMarketPreferences = OnlineMarketPreferences.getInstance(application);

    }

    public void setSearchResultProductsLiveData(Map<String, String> query) {
        mProductRepository.setSearchResultProductsLiveData(query);
    }

    public LiveData<List<Product>> getSearchResultProductsLiveData() {
        return mProductRepository.getSearchResultProductsLiveData();
    }


    public void setOrderedSearchResultProductsLiveData(String orderBy) {
        Map<String, String> newSearchQueryMap = NetworkParams.
                getOrderedSearchResultProducts(mOnlineMarketPreferences.getQueryMap()
                        , orderBy);
        setSearchResultProductsLiveData(newSearchQueryMap);

    }

    public void setUserSelectedProduct(Product product){

        mProductRepository.setUserSelectedProduct(product);
    }

}
