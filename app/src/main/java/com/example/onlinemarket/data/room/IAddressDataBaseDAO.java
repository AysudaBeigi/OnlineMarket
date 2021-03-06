package com.example.onlinemarket.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlinemarket.data.model.customer.Address;

import java.util.List;

    @Dao
public interface IAddressDataBaseDAO {

        @Insert
        void insertAddress(Address address);

        @Update
        void updateAddress(Address address);

        @Delete
        void deleteAddress(Address address);

        @Query("select * from Address")
        LiveData<List<Address>> getAddresses();

        @Query("select * from Address where addressId = :addressId")
        Address getAddress(Integer addressId);

        @Query("delete from Address")
        void deleteAllAddresses();

}
