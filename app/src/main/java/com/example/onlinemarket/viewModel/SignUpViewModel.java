package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.repository.CustomerDBRepository;

public class SignUpViewModel  extends AndroidViewModel {
    private CustomerDBRepository mCustomerDBRepository;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        mCustomerDBRepository = CustomerDBRepository.getInstance(application);

    }
    public void insertAndPostCustomer(String  email){
        Customer customer = new Customer();
        customer.setEmail(email);
        mCustomerDBRepository.insertCustomer(customer);
        postCustomer(customer);

    }
    public void postCustomer(Customer customer){
        mCustomerDBRepository.postCustomer(customer);
    }
}
