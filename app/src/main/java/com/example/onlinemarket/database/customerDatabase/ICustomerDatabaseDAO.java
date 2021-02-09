package com.example.onlinemarket.database.customerDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.onlinemarket.model.customer.Customer;


@Dao
public interface ICustomerDatabaseDAO {

    @Insert
    void insertCustomer(Customer  customer);

    @Update
    void updateCustomer(Customer customer);

    @Query("SELECT * FROM customerTable")
    Customer getCustomer();

    @Delete
    void deleteCustomer(Customer customer);

}
