package com.example.onlinemarket.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlinemarket.data.model.Card;

import java.util.List;

@Dao
public interface ICartDatabaseDAO {


    @Update
    void updateCart(Card card);

    @Insert
    void insertCart(Card card);

    @Delete
    void deleteCart(Card card);

    @Query("DELETE FROM Card")
    void deleteAllCart();

    @Query("SELECT * FROM Card")
    List<Card> getCarts();

    @Query("SELECT * FROM Card WHERE product_id=:productId")
    Card getCart(int productId);

}
