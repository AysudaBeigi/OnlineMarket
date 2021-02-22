package com.example.onlinemarket.data.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.example.onlinemarket.data.remote.retrofit.WooCommerceAPIService;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private static String TAG = "OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;
    private MutableLiveData<List<Product>> mLatestProductsLiveData;
    private MutableLiveData<List<Product>> mPopularProductsLiveData;
    private MutableLiveData<List<Product>> mAmazingOfferProductsLiveData;
    private MutableLiveData<List<Product>> mMostVisitedProductsLiveData;
    private MutableLiveData<List<Product>> mCategoryProductsLiveData;
    private MutableLiveData<List<Product>> mSearchResultProductsLiveData;
    private MutableLiveData<Product> mSpecialProductLiveData;
    private static ProductRepository sInstance;
    private Product mUserSelectedProduct;
    private Context mContext;

    public static ProductRepository getInstance(Context context) {

        if (sInstance == null)
            sInstance = new ProductRepository(context);
        return sInstance;
    }


    private ProductRepository(Context context) {
        mContext=context.getApplicationContext();
        mWooCommerceAPIService = RetrofitInstance.getInstance(mContext).getRetrofit().
                create(WooCommerceAPIService.class);

        mLatestProductsLiveData = new MutableLiveData<>();
        mMostVisitedProductsLiveData = new MutableLiveData<>();
        mPopularProductsLiveData = new MutableLiveData<>();
        mAmazingOfferProductsLiveData = new MutableLiveData<>();
        mSearchResultProductsLiveData = new MutableLiveData<>();
        mSpecialProductLiveData = new MutableLiveData<>();
        mCategoryProductsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Product>> getLatestProductsLiveData() {
        return mLatestProductsLiveData;
    }

    public MutableLiveData<List<Product>> getPopularProductsLiveData() {
        return mPopularProductsLiveData;
    }

    public MutableLiveData<List<Product>> getAmazingOfferProductsLiveData() {
        return mAmazingOfferProductsLiveData;
    }

    public MutableLiveData<List<Product>> getMostVisitedProductsLiveData() {
        return mMostVisitedProductsLiveData;
    }

    public MutableLiveData<List<Product>> getCategoryProductsLiveData() {
        return mCategoryProductsLiveData;
    }

    public MutableLiveData<List<Product>> getSearchResultProductsLiveData() {
        return mSearchResultProductsLiveData;
    }

    public MutableLiveData<Product> getSpecialProductLiveData() {
        return mSpecialProductLiveData;
    }


    public void setLatestProductsLiveData() {
        Log.d(TAG, "MarketRepository : fetchLastProducts");

        mWooCommerceAPIService.getProducts(NetworkParams.getLastProducts()).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        List<Product> lastProducts = response.body();
                        mLatestProductsLiveData.setValue(lastProducts);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }


    public void setMostVisitedProductsLiveData() {
        Log.d(TAG, "MarketRepository : fetchMostVisitedProducts");

        mWooCommerceAPIService.getProducts(NetworkParams.getMostVisitedProducts()).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call,
                                           Response<List<Product>> response) {
                        List<Product> mostVisitedProducts = response.body();
                        mMostVisitedProductsLiveData.setValue(mostVisitedProducts);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void setPopularProductsLiveData() {

        Log.d(TAG, "MarketRepository : fetchPopularProducts");
        mWooCommerceAPIService.getProducts(NetworkParams.getPopularProducts()).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call,
                                           Response<List<Product>> response) {
                        List<Product> products = response.body();
                        mPopularProductsLiveData.setValue(products);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }

    public void setAmazingOfferProductsLiveData() {

        Log.d(TAG, "MarketRepository : fetchPopularProducts");
        mWooCommerceAPIService.getProducts(NetworkParams.getAmazingOfferProducts()).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call,
                                           Response<List<Product>> response) {
                        List<Product> products = response.body();
                        mAmazingOfferProductsLiveData.setValue(products);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }


    public void setCategoryProductsLiveData(int categoryId) {
        mWooCommerceAPIService.getProducts(NetworkParams.getCategoryProducts(categoryId)).
                enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call,
                                           Response<List<Product>> response) {
                        List<Product> categoryProducts = response.body();
                        mCategoryProductsLiveData.setValue(categoryProducts);
                    }
                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }


    public void setSpecialProductLiveData() {
        mWooCommerceAPIService.getProduct(NetworkParams.SPECIAL_PROUCT_ID,
                NetworkParams.getBaseQuery()).
                enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call,
                                           Response<Product> response) {
                        Product product = response.body();
                        mSpecialProductLiveData.setValue(product);
                    }
                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.e(TAG, t.getMessage(), t);
                    }
                });
    }


    public void setSearchResultProductsLiveData(Map<String, String> query) {
        mWooCommerceAPIService.getProducts(query).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {
                List<Product> searchItems = response.body();
                mSearchResultProductsLiveData.setValue(searchItems);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void setUserSelectedProduct(Product userSelectedProduct) {
        mUserSelectedProduct = userSelectedProduct;
    }

    public Product getUserSelectedProduct() {
        return mUserSelectedProduct;
    }
    public int getUserSelectedProductId(){
        return mUserSelectedProduct.getId();
    }
     public String getUserSelectedProductName(){
        return mUserSelectedProduct.getName();
    }
    public List<Image> getUserSelectedProductImages(){
        return mUserSelectedProduct.getImages();
    }

    public String getUserSelectedProductPrice(){
        return mUserSelectedProduct.getPrice() + " " +
                mContext.getResources().getString(R.string.toman);
    }

    public String getUserSelectedProductRegularPrice(){
        return mUserSelectedProduct.getRegularPrice() + " " +
                mContext.getResources().getString(R.string.toman);
    }


   /* public void fetchSearchFilteredProducts(String query, productsCallback callBacks) {

        HashMap<String, String> insideMap = new HashMap<>();

        insideMap.putAll(BASE_KEYS);
        insideMap.put("search", query);
        insideMap.put("attribute", "pa_color");
        insideMap.put("attribute_term", colorId);

        mRequestService.getProducts(insideMap).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {
                List<Product> searchItems = response.body();

                //update adapter of recyclerview
                callBacks.onItemResponse(searchItems);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
*/


}
