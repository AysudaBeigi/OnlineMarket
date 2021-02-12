package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.database.ICartDatabaseDAO;
import com.example.onlinemarket.database.OnlineMarketDatabase;
import com.example.onlinemarket.model.Card;

import java.util.List;

public class CartDBRepository implements ICartRepository {

    private static CartDBRepository sInstance;

    private ICartDatabaseDAO mCartDAO;
    private Context mContext;

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
    public void updateCart(Card card) {
        mCartDAO.updateCart(card);
    }


    @Override
    public void insertCart(Card card) {
        mCartDAO.insertCart(card);
    }

    @Override
    public void deleteCart(Card card) {
        mCartDAO.deleteCart(card);
    }

    @Override
    public void deleteAllCart() {
        mCartDAO.deleteAllCart();
    }

    @Override
    public List<Card> getCarts() {
        return mCartDAO.getCarts();
    }

    @Override
    public Card getCart(int productId) {
        return mCartDAO.getCart(productId);
    }


}
