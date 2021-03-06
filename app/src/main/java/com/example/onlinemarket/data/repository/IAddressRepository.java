package com.example.onlinemarket.data.repository;

import androidx.lifecycle.LiveData;

import com.example.onlinemarket.data.model.customer.Address;

import java.util.List;

public interface IAddressRepository {


    void insertAddress(Address address);

    void updateAddress(Address address);

    void deleteAddress(Address address);

    LiveData<List<Address>> getAddresses();

    Address getAddress(Integer addressId);

    void deleteAllAddresses();

}
