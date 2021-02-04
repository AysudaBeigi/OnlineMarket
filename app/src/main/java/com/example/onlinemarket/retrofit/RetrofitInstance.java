package com.example.onlinemarket.retrofit;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.example.onlinemarket.network.NetworkParams.BASE_URL;
public class RetrofitInstance {

    public static Retrofit getInstance(Context context) {
        int cacheSize = 100 * 1024 * 1024; // 100 MB
        Cache cache = new Cache(context.getApplicationContext().getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).
                        build();
    }

}
