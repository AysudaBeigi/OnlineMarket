package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.AddressesAdapter;
import com.example.onlinemarket.viewModel.AddressesViewModel;
import com.google.android.material.button.MaterialButton;

public class AddressesFragment extends Fragment {
    private MaterialButton mButtonAddAddress;
    private RecyclerView mRecyclerViewAddresses;
    private AddressesViewModel mAddressesViewModel;
    private NavController mNavController;


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
        mRecyclerViewAddresses.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupAdapter() {
        AddressesAdapter addressesAdapter = new AddressesAdapter(mAddressesViewModel);
        mRecyclerViewAddresses.setAdapter(addressesAdapter);
    }

    private void findViews(View view) {
        mRecyclerViewAddresses = view.findViewById(R.id.recycler_view_addresses);
        mButtonAddAddress = view.findViewById(R.id.button_add_address);
    }
}