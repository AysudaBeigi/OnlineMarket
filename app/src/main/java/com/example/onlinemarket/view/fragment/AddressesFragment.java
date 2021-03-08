package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.AddressesAdapter;
import com.example.onlinemarket.viewModel.AddressesViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class AddressesFragment extends VisibleFragment {
    private MaterialButton mButtonAddAddress;
    private RecyclerView mRecyclerViewAddresses;
    private AddressesViewModel mAddressesViewModel;
    private NavController mNavController;
    private MaterialTextView mTextViewHaveNotAnyAddress;


    public AddressesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddressesViewModel = new ViewModelProvider(this)
                .get(AddressesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addresses, container,
                false);
        findViews(view);

        initViews();
        setupAdapter();
        setListeners();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
    }

    private void setListeners() {
        mButtonAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_AddressesFragment_to_AddAddressFragment);
            }
        });
    }

    private void initViews() {
        if(mAddressesViewModel.getAddresses().size()==0){
            Log.d(TAG,"mAddressesViewModel.getAddresses()==null");
            mTextViewHaveNotAnyAddress.setVisibility(View.VISIBLE);
            mRecyclerViewAddresses.setVisibility(View.GONE);
        }else {
            Log.d(TAG,"mAddressesViewModel.getAddresses()!=null");

            mTextViewHaveNotAnyAddress.setVisibility(View.GONE);
            mRecyclerViewAddresses.setVisibility(View.VISIBLE);
        }
        mRecyclerViewAddresses.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupAdapter() {
        AddressesAdapter addressesAdapter = new AddressesAdapter(mAddressesViewModel);
        mRecyclerViewAddresses.setAdapter(addressesAdapter);
    }

    private void findViews(View view) {
        mRecyclerViewAddresses = view.findViewById(R.id.recycler_view_addresses);
        mButtonAddAddress = view.findViewById(R.id.button_add_address);
        mTextViewHaveNotAnyAddress =view.findViewById(R.id.text_view_have_not_any_address);
    }
}