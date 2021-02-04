package com.example.onlinemarket.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.onlinemarket.model.Product;

@Database(entities = Product.class, version = 1)
@TypeConverters({Converter.class})
public abstract class ProductDB extends RoomDatabase {
    public abstract ProductDAO getProductDataBaseDAO();
}
