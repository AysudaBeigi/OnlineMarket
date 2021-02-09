package com.example.onlinemarket.database;

import androidx.room.TypeConverter;

import com.example.onlinemarket.model.product.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converter {

    @TypeConverter
    public static Product stringToProduct(String productStr) {
        Gson gson = new Gson();
        if (productStr == null) {
            return new Product();
        }

        Type type = new TypeToken<Product>() {
        }.getType();
        return gson.fromJson(productStr, type);
    }

    @TypeConverter
    public static String productToString(Product product) {
        Gson gson = new Gson();
        return gson.toJson(product);
    }

}

