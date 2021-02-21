package com.example.onlinemarket.data.remote.retrofit;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.onlinemarket.data.remote.NetworkParams.BASE_URL;

public class RetrofitInstance {
    private static RetrofitInstance sInstance;
    private Retrofit mRetrofit;

    public static RetrofitInstance getInstance(Context context) {
        if (sInstance == null)
            sInstance = new RetrofitInstance(context);
        return sInstance;
    }


    private RetrofitInstance(Context context) {
        int cacheSize = 100 * 1024 * 1024; // 100 MB
        Cache cache = new Cache(context.getApplicationContext().getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).
                        build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }


}
