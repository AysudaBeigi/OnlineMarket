package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.database.productDatabase.IShoppingBagProductsDatabaseDAO;
import com.example.onlinemarket.model.product.Product;

import java.util.List;


public class ShoppingBagProductsRepository {

    private static ShoppingBagProductsRepository sInstance;

    private IShoppingBagProductsDatabaseDAO mIShoppingBagProductsDatabaseDAO;
    private Context mContext;


    private ShoppingBagProductsRepository(Context context) {

        mContext = context.getApplicationContext();
        ShoppingBagProductsDB productDataBase = Room.databaseBuilder(mContext,
                ShoppingBagProductsDB.class,
                "onlineMarket.db")
                .allowMainThreadQueries()
                .build();
        mIShoppingBagProductsDatabaseDAO = productDataBase.getProductDataBaseDAO();
    }


    public static ShoppingBagProductsRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ShoppingBagProductsRepository(context);
        return sInstance;
    }

    public void insertProduct(Product productsItem) {
        mIShoppingBagProductsDatabaseDAO.insertProduct(productsItem);
    }

    public void deleteProduct(Product productsItem) {
        mIShoppingBagProductsDatabaseDAO.deleteProduct(productsItem);
    }

    public Product getProduct(int productId) {
        return mIShoppingBagProductsDatabaseDAO.getProductItem(productId);
    }

    public List<Product> getProducts() {
        return mIShoppingBagProductsDatabaseDAO.getProducts();
    }
}
