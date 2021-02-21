package com.example.onlinemarket.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.Card;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CardDBRepository;
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
                .inflate(R.layout.card_item_view, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Product order = mOrderList.get(position);
        holder.bindProduct(order);
        //holder.setListener();
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
            setListener(itemView);
        }


        private void findHolderViews(@NonNull View itemView) {
            mTextViewName = itemView.findViewById(R.id.text_View_name_card_item);
            mTextViewPlus = itemView.findViewById(R.id.card_plus_button);
            mTextViewCount = itemView.findViewById(R.id.cout_card_item);
            mTextViewMinus = itemView.findViewById(R.id.text_view_minus_card_item);
            mImageViewTrash = itemView.findViewById(R.id.image_view_trash_card_item);
            mImageViewProduct = itemView.findViewById(R.id.image_view_card_item);
            mTextViewBasePriceCard = itemView.findViewById(R.id.text_view_price_card_item);

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

        private void setListener(View view) {
            mTextViewPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateCountAndPrice(++mProductCount);
                    checkVisibility();
                }
            });

            mTextViewMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCountAndPrice(--mProductCount);
                    checkVisibility();
                }
            });

            mImageViewTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCardDBRepository.deleteCart(mCard);
                   NavController navController=Navigation.findNavController(view);
                   navController.navigate(
                           R.id.action_ShoppingBagFragment_to_ShoppingBagFragment);

                }
            });

        }

        private void checkVisibility() {
            if (mProductCount > 1) {
                mTextViewMinus.setVisibility(View.VISIBLE);
                mImageViewTrash.setVisibility(View.GONE);

            } else {
                mTextViewMinus.setVisibility(View.GONE);
                mImageViewTrash.setVisibility(View.VISIBLE);

            }
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

