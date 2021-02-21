package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.repository.CustomerDBRepository;
import com.example.onlinemarket.databinding.FragmentUserProfileBinding;

public class UserProfileFragment extends Fragment {
    private Customer mCustomer;
    public static String TAG = "OnlineMarket";
    private FragmentUserProfileBinding mBinding;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    public static UserProfileFragment newInstance() {
        Log.d(TAG, "UserProfileFragment + newInstance ");
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "UserProfileFragment + onCreate ");

        mCustomer = CustomerDBRepository.getInstance(getActivity()).
                getCustomer();


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

        if (mCustomer != null) {
            Log.d(TAG, "UserProfileFragment + mCustomer!=null ");
            Log.d(TAG, "UserProfileFragment + mCustomer email is : " + mCustomer.getEmail());

            mBinding.textViewUserEmail.setText(CustomerDBRepository.getInstance(getActivity()).
                    getCustomer().getEmail());

        }
    }

}                           