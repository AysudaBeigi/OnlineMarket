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

import com.example.onlinemarket.R;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.AddressDetailViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddressDetailFragment extends Fragment {


    private TextInputEditText mEditTextCompleteAddress;
    private TextInputEditText mEditTextAddressName;
    private MaterialButton mButtonRegisterAddress;
    private AddressDetailViewModel mAddressDetailViewModel;
    private NavController mNavController;


    public AddressDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddressDetailViewModel = new ViewModelProvider(this)
                .get(AddressDetailViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_detail,
                container, false);
        findViews(view);
        initViews();
        setListeners(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController= Navigation.findNavController(view);
    }

    private void setListeners(View view) {
        mButtonRegisterAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String completeAddress = mEditTextCompleteAddress.getText().toString();
                String addressName = mEditTextAddressName.getText().toString();
                if (completeAddress.isEmpty() || addressName.isEmpty()) {
                    UIUtils.makeSnackBar(
                            view.findViewById(
                                    R.id.layout_complete_address_detail_show_snack_bar)
                            , R.string.complete_address_detail).show();

                } else {
                    mAddressDetailViewModel.insertAddress(completeAddress,addressName);
                    mNavController.navigate(R.id.action_AddressDetailFragment_to_AddressesFragment);
                }
            }
        });
    }

    private void initViews() {
        mEditTextAddressName.setText(mAddressDetailViewModel.getUnRegisteredAddressName());
        mEditTextCompleteAddress.setText(mAddressDetailViewModel.
                getUnRegisteredAddressInformation());
    }

    private void findViews(View view) {
        mEditTextCompleteAddress = view.findViewById(R.id.edit_text_complete_address_detail);
        mEditTextAddressName = view.findViewById(R.id.edit_text_address_name_address_detail);
        mButtonRegisterAddress = view.findViewById(R.id.button_register_address);
    }
}