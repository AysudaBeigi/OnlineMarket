package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.database.OnlineMarketDatabase;
import com.example.onlinemarket.database.productDatabase.IShoppingBagProductsDatabaseDAO;
import com.example.onlinemarket.model.product.Product;

import java.util.List;


public class ShoppingBagProductsRepository {

    private static ShoppingBagProductsRepository sInstance;

    private IShoppingBagProductsDatabaseDAO mIShoppingBagProductsDatabaseDAO;
    private Context mContext;

    public static ShoppingBagProductsRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ShoppingBagProductsRepository(context);
        return sInstance;
    }
    private ShoppingBagProductsRepository(Context context) {

        mContext = context.getApplicationContext();
        OnlineMarketDatabase onlineMarketDatabase = Room.databaseBuilder(mContext,
                OnlineMarketDatabase.class,
                "onlineMarket.db")
                .allowMainThreadQueries()
                .build();
        mIShoppingBagProductsDatabaseDAO = onlineMarketDatabase.
                getShoppingBagProductsDatabaseDAO();
    }



    public void insertProduct(Product product) {
        mIShoppingBagProductsDatabaseDAO.insertProduct(product);
    }

    public void deleteProduct(Product product) {
        mIShoppingBagProductsDatabaseDAO.deleteProduct(product);
    }

    public Product getProduct(int productId) {
        return mIShoppingBagProductsDatabaseDAO.getProductItem(productId);
    }

    public List<Product> getProducts()
    {
        return mIShoppingBagProductsDatabaseDAO.getProducts();
    }
}
