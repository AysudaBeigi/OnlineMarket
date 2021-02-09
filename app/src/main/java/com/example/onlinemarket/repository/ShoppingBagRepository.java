package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.database.productDatabase.IShoppingBagProductsDAO;
import com.example.onlinemarket.database.productDatabase.ShoppingBagProductsDB;
import com.example.onlinemarket.model.product.Product;

import java.util.List;


public class ShoppingBagRepository {

    private static ShoppingBagRepository sInstance;

    private IShoppingBagProductsDAO mIShoppingBagProductsDAO;
    private Context mContext;


    private ShoppingBagRepository(Context context) {

        mContext = context.getApplicationContext();
        ShoppingBagProductsDB productDataBase = Room.databaseBuilder(mContext,
                ShoppingBagProductsDB.class,
                "word.db")
                .allowMainThreadQueries()
                .build();
        mIShoppingBagProductsDAO = productDataBase.getProductDataBaseDAO();
    }


    public static ShoppingBagRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ShoppingBagRepository(context);
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
