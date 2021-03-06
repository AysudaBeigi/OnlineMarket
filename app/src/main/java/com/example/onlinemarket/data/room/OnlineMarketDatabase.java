package com.example.onlinemarket.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.onlinemarket.data.model.Card;
import com.example.onlinemarket.data.model.customer.Address;
import com.example.onlinemarket.data.model.customer.Customer;

@Database(entities = {Customer.class, Card.class, Address.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class OnlineMarketDatabase extends RoomDatabase {
    public abstract ICustomerDatabaseDAO getCustomerDatabaseDAO();
    public abstract ICartDatabaseDAO getCartDatabaseDAO();
    public abstract IAddressDataBaseDAO getAddressDataBaseDAO();

}
