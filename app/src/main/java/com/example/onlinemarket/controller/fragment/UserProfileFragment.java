package com.example.onlinemarket.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.repository.CustomerDBRepository;
import com.google.android.material.textview.MaterialTextView;

public class UserProfileFragment extends Fragment {
    private MaterialTextView mTextViewUserEmail;
    public UserProfileFragment() {
        // Required empty public constructor
    }


    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        if(CustomerDBRepository.getInstance(getActivity()).
                getCustomer()!=null)
        mTextViewUserEmail.setText(CustomerDBRepository.getInstance(getActivity()).
                getCustomer().getEmail());
    }

    private void findViews(View view) {
        mTextViewUserEmail=view.findViewById(R.id.text_view_user_email);
    }
}