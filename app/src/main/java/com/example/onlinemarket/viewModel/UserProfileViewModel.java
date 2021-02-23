package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.data.repository.CustomerDBRepository;

public class UserProfileViewModel extends AndroidViewModel {
    private CustomerDBRepository mCustomerDBRepository;

    public UserProfileViewModel(@NonNull Application application) {
        super(application);
        mCustomerDBRepository= CustomerDBRepository.getInstance(application);

    }

    public boolean isCustomerExist(){
        return mCustomerDBRepository.getCustomer()!=null;
    }
    public String  getCustomerEmail(){
        return mCustomerDBRepository.getCustomer().getEmail();
    }


}
