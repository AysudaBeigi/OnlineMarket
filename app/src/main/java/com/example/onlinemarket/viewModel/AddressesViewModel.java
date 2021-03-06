package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.data.model.customer.Address;
import com.example.onlinemarket.data.repository.AddressRepository;

import java.util.List;

public class AddressesViewModel extends AndroidViewModel {
    private AddressRepository mAddressRepository;

    public AddressesViewModel(@NonNull Application application) {
        super(application);
        mAddressRepository = AddressRepository.getInstance(getApplication());

    }

    public List<Address> getAddresses() {
        return mAddressRepository.getAddresses();
    }


}
