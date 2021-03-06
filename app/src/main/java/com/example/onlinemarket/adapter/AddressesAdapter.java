package com.example.onlinemarket.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddressesAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private List<MyAddress> mItems;
    private FinishShoppingViewModel mAddressViewModel;

    public List<MyAddress> getItems() {
        return mItems;
    }

    public void setItems(List<MyAddress> items) {
        mItems = items;
    }

    public AddressAdapter(FinishShoppingViewModel addressViewModel) {
        mAddressViewModel = addressViewModel;
        mItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemAddressBinding listItemAddressBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_address,
                parent,
                false
        );
        return new AddressViewHolder(listItemAddressBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.bindAddress(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        ListItemAddressBinding mBinding;

        public AddressViewHolder(@NonNull ListItemAddressBinding itemAddressBinding) {
            super(itemAddressBinding.getRoot());
            mBinding = itemAddressBinding;
            mBinding.setViewmodel(mAddressViewModel);

        }

        public void bindAddress(MyAddress myAddress) {
            mBinding.setAddress(myAddress);
        }
    }
}
