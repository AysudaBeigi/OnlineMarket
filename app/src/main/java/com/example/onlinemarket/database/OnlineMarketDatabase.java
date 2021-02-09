package com.example.onlinemarket.database;

import androidx.room.Database;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.onlinemarket.database.customerDatabase.ICustomerDatabaseDAO;
import com.example.onlinemarket.database.productDatabase.IShoppingBagProductsDatabaseDAO;
import com.example.onlinemarket.model.customer.Customer;

@Database(entities = {Customer.class, PrimaryKey.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class OnlineMarketDatabase extends RoomDatabase {
    public abstract ICustomerDatabaseDAO getCustomerDatabaseDAO();

    public abstract IShoppingBagProductsDatabaseDAO getShoppingBagProductsDatabaseDAO();

}
