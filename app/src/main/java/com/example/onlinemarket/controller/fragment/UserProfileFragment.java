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
import com.google.android.material.textview.MaterialTextView;

public class UserProfileFragment extends Fragment {
    private MaterialTextView mTextViewUserEmail;
    private Customer mCustomer;
    public static String TAG="OnlineMarket";

    public UserProfileFragment() {
        // Required empty public constructor
    }


    public static UserProfileFragment newInstance() {
        Log.d(TAG,"UserProfileFragment + newInstance ");
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG,"UserProfileFragment + onCreate ");

        mCustomer=CustomerDBRepository.getInstance(getActivity()).
                getCustomer();

        if(mCustomer==null){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_main_activity,
                            SignUpFragment.newInstance())
                    .commit();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_profile,
                container, false);

        findViews(view);

        initViews();

        return view;
    }

    private void initViews() {
        Log.d(TAG,"UserProfileFragment + initViews ");

        if(mCustomer!=null){
            Log.d(TAG,"UserProfileFragment + mCustomer!=null ");
            Log.d(TAG,"UserProfileFragment + mCustomer email is : "+mCustomer.getEmail());

            mTextViewUserEmail.setText(CustomerDBRepository.getInstance(getActivity()).
                    getCustomer().getEmail());

        }
    }




    private void findViews(View view) {
        Log.d(TAG,"UserProfileFragment + findViews ");

        mTextViewUserEmail=view.findViewById(R.id.text_view_user_email);
    }
}