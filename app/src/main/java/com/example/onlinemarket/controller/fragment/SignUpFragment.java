package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends Fragment {
    private TextInputEditText mEditTextEmail;
    private MaterialButton mButtonEnterOnlineMarket;
    private String mCustomerEmail;
    private CustomerDBRepository mCustomerDBRepository;
    public static String TAG="OnlineMarket";

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
        View view = inflater.inflate(R.layout.fragment_sign_up, container,
                false);
        Log.d(TAG, "SignUpFragment + onCreateView ");

        findViews(view);
        setListeners(view);
        return view;
    }

    private void setListeners(View view) {
        mButtonEnterOnlineMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomerEmail=mEditTextEmail.getText().toString();

                if (mCustomerEmail.isEmpty()) {
                    showEmailCantEmptySnackBar(view);
                } else {
                    SignUpCustomer();
                    replaceUserProfileFragment();

                }
            }
        });
    }

    private void replaceUserProfileFragment() {
        Log.d(TAG,"SignUpCustomer +replaceUserProfileFragment");

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main_activity,
                        UserProfileFragment.newInstance())
                .commit();
    }

    private void SignUpCustomer() {
        Customer customer = new Customer();
        customer.setEmail(mCustomerEmail);
        mCustomerDBRepository.postCustomer(customer, new CustomerDBRepository.CustomerCallback() {
            @Override
            public void onItemResponse(Customer customer) {
                mCustomerDBRepository.insertCustomer(customer);
                Log.d(TAG,"SignUpCustomer +postCustomer+ onItemResponse");
                Log.d(TAG,"SignUpCustomer+onItemResponse customer emali is "+customer.getEmail());

            }
        });
    }

    private void showEmailCantEmptySnackBar(View view) {
        Log.d(TAG, "SignUpFragment + showEmailCantEmptySnackBar ");

        Snackbar snackbar = Snackbar
                .make(view.findViewById(R.id.layout_show_email_cant_empty_snack_bar),
                        "ابتدا ایمیل خود را وارد کنید",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    private void findViews(View view) {
        Log.d(TAG, "SignUpFragment + findViews ");

        mEditTextEmail = view.findViewById(R.id.text_input_edit_text_user_email);
        mButtonEnterOnlineMarket = view.findViewById(R.id.button_enter_online_market);
    }
}