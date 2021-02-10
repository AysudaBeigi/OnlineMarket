package com.example.onlinemarket.controller.fragment;

import android.graphics.Color;
import android.os.Bundle;
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

    public SignUpFragment() {
        // Required empty public constructor
    }


    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomerDBRepository = CustomerDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container,
                false);
        findViews(view);
        setListeners(view);
        return view;
    }

    private void setListeners(View view) {
        mButtonEnterOnlineMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomerEmail = mEditTextEmail.getText().toString();
                if (mCustomerEmail.equals("")) {
                    showEmailCantEmptySnackBar(view);

                } else {
                    SignUpCustomer();
                    //todo: going to pay the orders
                }
            }
        });
    }

    private void SignUpCustomer() {
        Customer customer = new Customer(mCustomerEmail);
        mCustomerDBRepository.postCustomer(customer, new CustomerDBRepository.CustomerCallback() {
            @Override
            public void onItemResponse(Customer customer) {
                mCustomerDBRepository.insertCustomer(customer);
            }
        });
    }

    private void showEmailCantEmptySnackBar(View view) {
        Snackbar snackbar = Snackbar
                .make(view.findViewById(R.id.layout_show_email_cant_empty_snack_bar),
                        "ابتدا ایمیل خود را وارد کنید",
                        Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(Color.RED);
        snackbar.show();
    }

    private void findViews(View view) {
        mEditTextEmail = view.findViewById(R.id.text_input_edit_text_user_email);
        mButtonEnterOnlineMarket = view.findViewById(R.id.button_enter_online_market);
    }
}