package com.example.onlinemarket.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlinemarket.model.Card;

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


/*

    @Update
    void updateAddress(MapAddress mapAddress);

    @Insert
    void insertAddress(MapAddress mapAddress);

    @Insert
    void insertAddresses(List<MapAddress> mapAddresses);

    @Delete
    void deleteAddress(MapAddress mapAddress);

    @Query("SELECT * FROM address")
    List<MapAddress> getAddresses();

    @Query("SELECT * FROM address WHERE selected_address=1")
    MapAddress getAddress();

    @Query("SELECT * FROM address WHERE primary_id=:addressId")
    MapAddress getAddressWithId(long addressId);
*/

}
