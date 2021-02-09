package com.example.onlinemarket.repository;

import com.example.onlinemarket.model.Cart;

import java.util.List;

public interface ICartRepository {

    void updateCart(Cart cart);


    void insertCart(Cart cart);

    void deleteCart(Cart cart);

    void deleteAllCart();

    List<Cart> getCarts();

    Cart getCart(int productId);

}
