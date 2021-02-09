package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.database.OnlineMarketDatabase;
import com.example.onlinemarket.database.customerDatabase.ICustomerDatabaseDAO;
import com.example.onlinemarket.model.customer.Customer;

public class CustomerRepository {

    private static CustomerRepository sInstance;

    private ICustomerDatabaseDAO mICustomerDatabaseDAO;
    private Context mContext;

    public static CustomerRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CustomerRepository(context);
        return sInstance;
    }
    private CustomerRepository(Context context) {
        mContext = context.getApplicationContext();
        OnlineMarketDatabase onlineMarketDatabase = Room.databaseBuilder(mContext,
                OnlineMarketDatabase.class,
                "onlineMarket.db")
                .allowMainThreadQueries()
                .build();
        mICustomerDatabaseDAO = onlineMarketDatabase.
                getCustomerDatabaseDAO();
    }



    public void insertCustomer(Customer customer) {
        mICustomerDatabaseDAO.insertCustomer(customer);
    }
    public void updateCustomer(Customer customer){
        mICustomerDatabaseDAO.updateCustomer(customer);
    }

    public void deleteCustomer(Customer customer) {
        mICustomerDatabaseDAO.deleteCustomer(customer);
    }

    public Customer getCustomer() {
        return mICustomerDatabaseDAO.getCustomer();
    }



}
