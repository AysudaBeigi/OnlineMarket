package com.example.onlinemarket.repository;

import android.content.Context;
import android.util.Log;

import com.example.onlinemarket.model.order.Order;
import com.example.onlinemarket.network.WooCommerceAPIService;
import com.example.onlinemarket.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    public static String TAG="OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;
    private static OrderRepository sInstance;

    public static OrderRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new OrderRepository(context);
        return sInstance;
    }

    private OrderRepository(Context context) {
        mWooCommerceAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(WooCommerceAPIService.class);

    }

    public void postOrder(Order order, OrderCallback orderCallback ){
        mWooCommerceAPIService.postOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                orderCallback.onItemResponse(order);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

            }
        });
    }




    public interface OrderCallback {
        void onItemResponse(Order order);
    }


}
