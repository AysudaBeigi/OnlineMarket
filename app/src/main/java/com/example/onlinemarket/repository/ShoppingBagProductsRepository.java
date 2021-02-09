package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.database.productDatabase.IShoppingBagProductsDAO;
import com.example.onlinemarket.database.productDatabase.ShoppingBagProductsDB;
import com.example.onlinemarket.model.product.Product;

import java.util.List;


public class ShoppingBagProductsRepository {

    private static ShoppingBagProductsRepository sInstance;

    private IShoppingBagProductsDAO mIShoppingBagProductsDAO;
    private Context mContext;


    private ShoppingBagProductsRepository(Context context) {

        mContext = context.getApplicationContext();
        ShoppingBagProductsDB productDataBase = Room.databaseBuilder(mContext,
                ShoppingBagProductsDB.class,
                "word.db")
                .allowMainThreadQueries()
                .build();
        mIShoppingBagProductsDAO = productDataBase.getProductDataBaseDAO();
    }


    public static ShoppingBagProductsRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ShoppingBagProductsRepository(context);
        return sInstance;
    }

    public void insertProduct(Product productsItem) {
        mIShoppingBagProductsDAO.insertProduct(productsItem);
    }

    public void deleteProduct(Product productsItem) {
        mIShoppingBagProductsDAO.deleteProduct(productsItem);
    }

    public Product getProduct(int productId) {
        return mIShoppingBagProductsDAO.getProductItem(productId);
    }

    public List<Product> getProducts() {
        return mIShoppingBagProductsDAO.getProducts();
    }
}