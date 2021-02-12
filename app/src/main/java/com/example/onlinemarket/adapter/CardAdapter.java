package com.example.onlinemarket.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.controller.fragment.ShoppingBagFragment;
import com.example.onlinemarket.model.Card;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CardDBRepository;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    public static final String TAG = "OnlineMarket";
    private Context mContext;
    private List<Product> mOrderList;
    private int mSumPriceCarts = 0;
    CardDBRepository mCardDBRepository;


    public List<Product> getOrderList() {
        return mOrderList;
    }

    public void setOrderList(List<Product> orderList) {
        mOrderList = orderList;
        notifyDataSetChanged();
    }

    public CardAdapter(Context context, List<Product> orderList) {
        mContext = context;
        mOrderList = orderList;
        mCardDBRepository = CardDBRepository.getInstance(mContext);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.cart_item_view, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Product order = mOrderList.get(position);
        holder.bindProduct(order);
        holder.setListener(position);
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mTextViewName;
        private MaterialTextView mTextViewPlus,
                mTextViewMinus;
        private MaterialTextView mTextViewCount, mTextViewBasePriceCard;
        private ShapeableImageView mImageViewProduct, mImageViewTrash;
        private Card mCard;
        private int mProductCount;
        private int basePriceCard = 0;
        private int mSumPriceCard = 0;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            findHolderViews(itemView);
        }


        private void findHolderViews(@NonNull View itemView) {
            mTextViewName = itemView.findViewById(R.id.card_name);
            mTextViewPlus = itemView.findViewById(R.id.card_plus_button);
            mTextViewCount = itemView.findViewById(R.id.card_count);
            mTextViewMinus = itemView.findViewById(R.id.card_minus_button);
            mImageViewTrash = itemView.findViewById(R.id.image_view_trash);
            mImageViewProduct = itemView.findViewById(R.id.image_view_card_item);
            mTextViewBasePriceCard = itemView.findViewById(R.id.text_view_product_price);

        }

        private void bindProduct(Product order) {
            Log.d(TAG, "bindProduct: order name is :" + order.getName());
            mCard = mCardDBRepository.getCart(order.getId());
            mProductCount = mCard.getProductCount();
            mTextViewName.setText(order.getName());
            basePriceCard = Integer.parseInt(order.getPrice());
            mTextViewBasePriceCard.setText(basePriceCard
                    + mContext.getResources().getString(R.string.toman));
            mTextViewCount.setText(mProductCount + "");
            mSumPriceCard = basePriceCard * mProductCount;
            mSumPriceCarts = mSumPriceCarts + mSumPriceCard;

            List<Image> imagesItems = order.getImages();
            if (imagesItems.get(0).getSrc().length() != 0)
                UIUtils.setImageUsingPicasso(imagesItems.get(0).getSrc(),
                        mImageViewProduct);

        }

        private void setListener(int position) {
            mTextViewPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCountAndPrice(++mProductCount);
                }
            });

            mTextViewMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mProductCount > 1) {
                        updateCountAndPrice(--mProductCount);
                    } else {
                        mTextViewMinus.setVisibility(View.GONE);
                        mImageViewTrash.setVisibility(View.VISIBLE);
                    }
                }
            });

            mImageViewTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCardDBRepository.deleteCart(mCard);
                    ((AppCompatActivity) mContext).getSupportFragmentManager()
                            .beginTransaction().
                            replace(R.id.fragment_container_main_activity,
                                    ShoppingBagFragment.newInstance())
                            .commit();


                }
            });

        }

        private void updateCountAndPrice(int productCount) {
            mCard.setProductCount(productCount);
            mCardDBRepository.updateCart(mCard);
            mTextViewCount.setText(productCount + "");
            updateSumPriceCarts();
        }

        private void updateSumPriceCarts() {
            mSumPriceCard = mProductCount * basePriceCard;
            mSumPriceCarts = mSumPriceCarts + mSumPriceCard;
        }

    }
}

