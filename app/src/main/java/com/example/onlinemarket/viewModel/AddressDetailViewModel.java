package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.data.model.customer.Address;
import com.example.onlinemarket.data.repository.AddressRepository;

public class AddressDetailViewModel extends AndroidViewModel {

    private AddressRepository mAddressRepository;
    public AddressDetailViewModel(@NonNull Application application) {
        super(application);
        mAddressRepository=AddressRepository.getInstance(getApplication());
    }
    public String getUnRegisteredAddressName(){
         return mAddressRepository.getUnRegisteredAddress().getName()+"";
    }
     public String getUnRegisteredAddressInformation(){
         return mAddressRepository.getUnRegisteredAddress().getInformation()+"";
    }

    public void insertAddress(String completeAddress,String AddressName ){
        Address address=new Address();
        address.setName(AddressName);
        address.setInformation(completeAddress);
        mAddressRepository.insertAddress(address);
    }

}
