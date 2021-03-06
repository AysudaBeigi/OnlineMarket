package com.example.onlinemarket.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.onlinemarket.data.model.customer.Address;
import com.example.onlinemarket.data.room.IAddressDataBaseDAO;
import com.example.onlinemarket.data.room.OnlineMarketDatabase;

import java.util.List;

public class AddressRepository implements IAddressRepository {
    public static final String TAG = "Address Repository";
    private static AddressRepository sInstance;
    private IAddressDataBaseDAO mAddressDAO;
    private Context mContext;


    public static  AddressRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new AddressRepository(context);
        return sInstance;
    }

    public AddressRepository(Context context) {
        mContext = context.getApplicationContext();
        OnlineMarketDatabase onlineMarketDatabase = Room.databaseBuilder(mContext,
                OnlineMarketDatabase.class,
                "onlineMarket.db")
                .allowMainThreadQueries()
                .build();

        mAddressDAO = onlineMarketDatabase.getAddressDataBaseDAO();

    }


    @Override
    public void insertAddress(Address address) {

        mAddressDAO.insertAddress(address);
    }

    @Override
    public void updateAddress(Address address) {

        mAddressDAO.updateAddress(address);
    }

    @Override
    public void deleteAddress(Address address) {

        mAddressDAO.deleteAddress(address);
    }

    @Override
    public List<Address> getAddresses() {
        return mAddressDAO.getAddresses();
    }

    @Override
    public Address getAddress(int addressId) {
        return mAddressDAO.getAddress(addressId);
    }

    @Override
    public void deleteAllAddresses() {
        mAddressDAO.deleteAllAddresses();
    }
}

