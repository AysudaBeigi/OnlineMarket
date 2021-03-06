package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentUserProfileBinding;
import com.example.onlinemarket.viewModel.UserProfileViewModel;

public class UserProfileFragment extends VisibleFragment {
    public static String TAG = "OnlineMarket";
    private FragmentUserProfileBinding mBinding;
    private UserProfileViewModel mUserProfileViewModel;
    private NavController mNavController;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "UserProfileFragment + onCreate ");
        mUserProfileViewModel=new ViewModelProvider(this)
                .get(UserProfileViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile,
                container, false);

        initViews();
        setListeners();
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.textViewAddressesUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_UserProfileFragment_to_AddressesFragment);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    private void initViews() {
        Log.d(TAG, "UserProfileFragment + initViews ");

        if (mUserProfileViewModel.isCustomerExist()) {
            Log.d(TAG, "UserProfileFragment + mCustomer!=null ");

            mBinding.setCustomerEmail(mUserProfileViewModel.getCustomerEmail());
            mBinding.imageViewNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNavController.navigate(R.id.action_UserProfileFragment_to_NotificationFragment);
                }
            });
           // mBinding.textViewUserEmail.setText(mUserProfileViewModel.getCustomerEmail());
        }
    }

}                           