package com.example.onlinemarket.repository;

import com.example.onlinemarket.model.Card;

import java.util.List;

public interface ICartRepository {

    void updateCart(Card card);


    void insertCart(Card card);

    void deleteCart(Card card);

    void deleteAllCart();

    List<Card> getCarts();

    Card getCart(int productId);

}
