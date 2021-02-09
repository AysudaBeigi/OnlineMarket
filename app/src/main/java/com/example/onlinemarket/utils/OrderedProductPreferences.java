package com.example.onlinemarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class OrderedProductPreferences {


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

}
