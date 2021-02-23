package com.example.onlinemarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class OnlineMarketPreferences {

    public static final String PREF_QUERY_MAP = "prefQueryMap";
    private Context mContext;
    private static OnlineMarketPreferences sInstance;

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

    public void setQueryMap(Map<String ,String > queryMap) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(queryMap);
        editor.putString(PREF_QUERY_MAP, json);
        editor.apply();

    }

    public Map<String,String> getQueryMap() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PREF_QUERY_MAP,
                null);
        Type type = new TypeToken<Map<String ,String >>() {

        }.getType();
        return gson.fromJson(json, type);

    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(mContext.getPackageName(),
                Context.MODE_PRIVATE);
    }


}
