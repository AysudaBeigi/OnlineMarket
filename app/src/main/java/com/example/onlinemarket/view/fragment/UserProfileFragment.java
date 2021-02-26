package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

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

        return mBinding.getRoot();
    }

    private void initViews() {
        Log.d(TAG, "UserProfileFragment + initViews ");

        if (mUserProfileViewModel.isCustomerExist()) {
            Log.d(TAG, "UserProfileFragment + mCustomer!=null ");

            mBinding.setCustomerEmail(mUserProfileViewModel.getCustomerEmail());
           // mBinding.textViewUserEmail.setText(mUserProfileViewModel.getCustomerEmail());
        }
    }

}                           