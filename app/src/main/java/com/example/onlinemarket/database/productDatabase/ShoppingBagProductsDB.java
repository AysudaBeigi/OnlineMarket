package com.example.onlinemarket.database.productDatabase;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.onlinemarket.model.product.Product;

@Database(entities = Product.class, version = 1)
@TypeConverters({ShoppingBagProductsConverter.class})
public abstract class ShoppingBagProductsDB extends RoomDatabase {
    public abstract IShoppingBagProductsDAO getProductDataBaseDAO();
}
