package com.example.onlinemarket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.customer.Address;
import com.example.onlinemarket.viewModel.AddressesViewModel;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.AddressViewHolder> {
    private AddressesViewModel mAddressViewModel;

    public AddressesAdapter(AddressesViewModel addressViewModel) {
        mAddressViewModel = addressViewModel;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.address_item_view, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.bindAddress(mAddressViewModel.getAddresses().get(position));
    }

    @Override
    public int getItemCount() {
        return mAddressViewModel.getAddresses().size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mTextViewAddressName;
        private MaterialTextView mTextViewAddressInformation;
        private MaterialRadioButton mRadioButtonSelectAddress;
        private Address mAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            findItemViews(itemView);

            setItemListener();

        }

        private void findItemViews(@NonNull View itemView) {
            mTextViewAddressName=itemView.findViewById(R.id.text_view_address_name_item_view);
            mTextViewAddressInformation=itemView.findViewById(R.id.text_view_address_information_item_view);
            mRadioButtonSelectAddress=itemView.findViewById(R.id.radio_button_select_address_item_view);
        }

        private void setItemListener() {
            mRadioButtonSelectAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mAddress.setSelected(true);
                    mAddressViewModel.updateAddress(mAddress);
                }
            });
        }

        public void bindAddress(Address address) {
            mAddress=address;
            mTextViewAddressName.setText(address.getName());
            mTextViewAddressInformation.setText(address.getInformation());

        }
    }
}
