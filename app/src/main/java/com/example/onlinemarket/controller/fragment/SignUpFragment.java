package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentSignUpBinding;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerDBRepository;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.snackbar.Snackbar;

public class SignUpFragment extends Fragment {
    private String mCustomerEmail;
    private CustomerDBRepository mCustomerDBRepository;
    public static String TAG = "OnlineMarket";
    private NavController mNavController;
    private FragmentSignUpBinding mBinding;

    public SignUpFragment() {
        // Required empty public constructor
    }


    public static SignUpFragment newInstance() {
        Log.d(TAG, "SignUpFragment + newInstance ");

        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SignUpFragment + onCreate ");

        mCustomerDBRepository = CustomerDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sign_up, container,
                false);
        Log.d(TAG, "SignUpFragment + onCreateView ");

        setListeners();
        return mBinding.getRoot();
    }

    private void setListeners() {
        mBinding.buttonEnterOnlineMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomerEmail = mBinding.textInputEditTextUserEmail.getText().toString();

                if (mCustomerEmail.isEmpty()) {
                    showEmailCantEmptySnackBar();
                } else {
                    SignUpCustomer();
                    replaceUserProfileFragment();

                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    private void replaceUserProfileFragment() {
        Log.d(TAG, "SignUpCustomer +replaceUserProfileFragment");

        mNavController.navigate(R.id.action_SignUpFragment_to_UserProfileFragment);

    }

    private void SignUpCustomer() {
        Customer customer = new Customer();
        customer.setEmail(mCustomerEmail);
        mCustomerDBRepository.insertCustomer(customer);
        mCustomerDBRepository.postCustomer(customer, new CustomerDBRepository.CustomerCallback() {
            @Override
            public void onItemResponse(Customer customer) {

            }
        });
    }

    private void showEmailCantEmptySnackBar() {
        Log.d(TAG, "SignUpFragment + showEmailCantEmptySnackBar ");
        Snackbar snackbar = UIUtils.makeSnackBar(mBinding.layoutShowEmailCantEmptySnackBar,
                R.string.first_enter_your_email);
        snackbar.show();
    }


}