package com.example.onlinemarket.database.customerDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.onlinemarket.model.customer.Customer;

@Database(entities = Customer.class, version = 1)
public  abstract class CustomerDB extends RoomDatabase {
    public abstract ICustomerDAO getCustomerDatabaseDAO();

}
