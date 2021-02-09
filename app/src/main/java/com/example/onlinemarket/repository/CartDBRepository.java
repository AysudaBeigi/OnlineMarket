package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.database.ICartDatabaseDAO;
import com.example.onlinemarket.database.OnlineMarketDatabase;
import com.example.onlinemarket.model.Cart;

import java.util.List;

public class CartDBRepository implements ICartRepository {

    private static CartDBRepository sInstance;

    private ICartDatabaseDAO mCartDAO;
    private Context mContext;
    private List<Cart> mCarts;

    public static CartDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CartDBRepository(context);

        return sInstance;
    }

    private CartDBRepository(Context context) {
        mContext = context.getApplicationContext();
        OnlineMarketDatabase onlineMarketDatabase= Room.databaseBuilder(mContext,
                OnlineMarketDatabase.class,
                "onlineMarket.db")
                .allowMainThreadQueries()
                .build();

        mCartDAO = onlineMarketDatabase.getCartDatabaseDAO();
    }

    @Override
    public void updateCart(Cart cart) {
        mCartDAO.updateCart(cart);
    }


    @Override
    public void insertCart(Cart cart) {
        mCartDAO.insertCart(cart);
    }

    @Override
    public void deleteCart(Cart cart) {
        mCartDAO.deleteCart(cart);
    }

    @Override
    public void deleteAllCart() {
        mCartDAO.deleteAllCart();
    }

    @Override
    public List<Cart> getCarts() {
        return mCartDAO.getCarts();
    }

    @Override
    public Cart getCart(int productId) {
        return mCartDAO.getCart(productId);
    }


}
