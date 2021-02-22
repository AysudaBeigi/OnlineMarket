package com.example.onlinemarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.onlinemarket.data.model.product.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class OnlineMarketPreferences {

    public static final String PREF_USER_SELECTED_PRODUCT = "prefUserSelectedProduct";
    private Context mContext;
    private static OnlineMarketPreferences sInstance;
    private SharedPreferences mSharedPreferences;

    private OnlineMarketPreferences(Context context) {

        mContext = context.getApplicationContext();
    }

    public static OnlineMarketPreferences getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new OnlineMarketPreferences(context);
            return sInstance;
        }
        return sInstance;
    }

    public void setUserSelectedProduct(Product product) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(product);
        editor.putString(PREF_USER_SELECTED_PRODUCT, json);
        editor.apply();

    }

    public Product getUserSelectedProduct() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(PREF_USER_SELECTED_PRODUCT,
                null);
        Type type = new TypeToken<Product>() {

        }.getType();
        return gson.fromJson(json, type);

    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(mContext.getPackageName(),
                Context.MODE_PRIVATE);
    }


}
