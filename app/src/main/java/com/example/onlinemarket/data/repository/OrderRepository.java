package com.example.onlinemarket.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.order.Order;
import com.example.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.example.onlinemarket.data.remote.retrofit.WooCommerceAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    public static String TAG="OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;
    private static OrderRepository sInstance;
    private MutableLiveData<Order> mPostOrderMutableLiveData;

    public static OrderRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new OrderRepository(context);
        return sInstance;
    }

    private OrderRepository(Context context) {
        mPostOrderMutableLiveData=new MutableLiveData<>();
        mWooCommerceAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(WooCommerceAPIService.class);

    }

    public MutableLiveData<Order> getPostOrderMutableLiveData() {
        return mPostOrderMutableLiveData;
    }

    public void setPostOrderLiveData(Order order ){
        mWooCommerceAPIService.postOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order=response.body();
                mPostOrderMutableLiveData.setValue(order);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

            }
        });
    }

}
