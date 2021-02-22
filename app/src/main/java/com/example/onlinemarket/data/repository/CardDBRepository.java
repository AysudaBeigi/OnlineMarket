package com.example.onlinemarket.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.data.model.Card;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.room.ICartDatabaseDAO;
import com.example.onlinemarket.data.room.OnlineMarketDatabase;

import java.util.List;

public class CardDBRepository implements ICartRepository {

    private static CardDBRepository sInstance;

    private ICartDatabaseDAO mCartDAO;
    private Context mContext;

    public static CardDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CardDBRepository(context);

        return sInstance;
    }

    private CardDBRepository(Context context) {
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


    public void addProductTooCard(Product product) {
        Card card = new Card(product, product.getId(), 1);
        insertCart(card);
    }
    public boolean isProductInCard(Product product) {
        Card card = new Card(product,product.getId(), 1);
        return getCarts().contains(card);
    }

}
