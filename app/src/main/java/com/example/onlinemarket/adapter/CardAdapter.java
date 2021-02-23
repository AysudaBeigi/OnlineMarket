package com.example.onlinemarket.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.Card;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.databinding.CardItemViewBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.ShoppingBagViewModel;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    public static final String TAG = "OnlineMarket";
    private Context mContext;
    private List<Product> mProductList;
    private CardItemViewBinding mBinding;
    private ShoppingBagViewModel mShoppingBagViewModel;


    public void setProductList(List<Product> productList) {
        mProductList = productList;
        notifyDataSetChanged();
    }

    public CardAdapter(Context context, List<Product> productList, ViewModelStoreOwner owner) {
        mContext = context;
        mProductList = productList;
        mShoppingBagViewModel=new ViewModelProvider(owner).get(ShoppingBagViewModel.class);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.card_item_view, parent, false);

        return new CardViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Product order = mProductList.get(position);
        holder.bindProduct(order);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private Card mCard;
        private int mProductCount;
        private int basePriceCard = 0;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            setListener();
        }

        private void bindProduct(Product order) {
            Log.d(TAG, "bindProduct: order name is :" + order.getName());
            mCard=mShoppingBagViewModel.getCart(order.getId());
            mProductCount = mCard.getProductCount();
            mBinding.textViewNameCardItem.setText(order.getName());
            basePriceCard = Integer.parseInt(order.getPrice());
            mBinding.textViewPriceCardItem.setText(basePriceCard
                    + mContext.getResources().getString(R.string.toman));
            mBinding.coutCardItem.setText(mProductCount + "");

            List<Image> imagesItems = order.getImages();
            if (imagesItems.get(0).getSrc().length() != 0)
                UIUtils.setImageUsingPicasso(imagesItems.get(0).getSrc(),
                        mBinding.imageViewCardItem);

        }

        private void setListener() {
            mBinding.cardPlusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateCountAndPrice(++mProductCount);
                    checkVisibility();
                }
            });

            mBinding.textViewMinusCardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCountAndPrice(--mProductCount);
                    checkVisibility();
                }
            });

            mBinding.imageViewTrashCardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mShoppingBagViewModel.deleteCart(mCard);
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(
                            R.id.action_ShoppingBagFragment_to_ShoppingBagFragment);

                }
            });

        }

        private void checkVisibility() {
            if (mProductCount > 1) {
                mBinding.textViewMinusCardItem.setVisibility(View.VISIBLE);
                mBinding.imageViewTrashCardItem.setVisibility(View.GONE);

            } else {
                mBinding.textViewMinusCardItem.setVisibility(View.GONE);
                mBinding.imageViewTrashCardItem.setVisibility(View.VISIBLE);

            }
        }

        private void updateCountAndPrice(int productCount) {
            mCard.setProductCount(productCount);
            mShoppingBagViewModel.updateCart(mCard);
            mBinding.coutCardItem.setText(productCount + "");
            setSumCardsPriceMutableLiveData();
        }

        private void setSumCardsPriceMutableLiveData() {
            mShoppingBagViewModel.setSumCardsPriceMutableLiveData();
        }

    }
}

