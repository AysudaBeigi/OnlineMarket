package com.example.onlinemarket.data.repository;

import com.example.onlinemarket.data.model.customer.Address;

import java.util.List;

public interface IAddressRepository {


    void insertAddress(Address address);

    void updateAddress(Address address);

    void deleteAddress(Address address);

    List<Address> getAddresses();

    Address getAddress(int addressId);

    void deleteAllAddresses();

}
