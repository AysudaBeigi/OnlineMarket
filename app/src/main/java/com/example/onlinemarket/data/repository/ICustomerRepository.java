package com.example.onlinemarket.data.repository;

import com.example.onlinemarket.data.model.customer.Customer;

public interface ICustomerRepository {

    void postCustomer(Customer customer, CustomerDBRepository.CustomerCallback
            customerCallbacks);

    void updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    Customer getCustomer();

}