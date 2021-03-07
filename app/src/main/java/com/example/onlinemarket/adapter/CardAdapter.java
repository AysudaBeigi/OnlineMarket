package com.example.onlinemarket.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.Card;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.ShoppingBagViewModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    public static final String TAG = "OnlineMarket";
    private Context mContext;
    private List<Product> mProductList;
    // private CardItemViewBinding mBinding;
    private ShoppingBagViewModel mShoppingBagViewModel;


    public void setProductList(List<Product> productList) {
        mProductList = productList;
        notifyDataSetChanged();
    }

    public CardAdapter(Context context, List<Product> productList, ViewModelStoreOwner owner) {
        mContext = context;
        mProductList = productList;
        mShoppingBagViewModel = new ViewModelProvider(owner).get(ShoppingBagViewModel.class);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.card_item_view, parent, false);
        /*mBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.card_item_view, parent, false);

        return new CardViewHolder(mBinding.getRoot());
 */
        return new CardViewHolder(view);
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
        private Product mProduct;
        private ShapeableImageView mImageViewCard;
        private ShapeableImageView mImageViewTrash;
        private MaterialTextView mTextViewName;
        private MaterialTextView mTextViewPrice;
        private MaterialTextView mTextViewCount;
        private MaterialTextView mTextViewMinus;
        private MaterialTextView mTextViewPlus;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            findItemViews(itemView);

            setListener();
        }

        private void findItemViews(@NonNull View itemView) {
            mImageViewCard=itemView.findViewById(R.id.image_view_card_item);
            mImageViewTrash=itemView.findViewById(R.id.image_view_trash_card_item);
            mTextViewPlus=itemView.findViewById(R.id.text_View_plus_card_item);
            mTextViewMinus=itemView.findViewById(R.id.text_view_minus_card_item);
            mTextViewCount=itemView.findViewById(R.id.text_view_count_card_item);
            mTextViewName=itemView.findViewById(R.id.text_View_name_card_item);
            mTextViewPrice=itemView.findViewById(R.id.text_view_price_card_item);

        }

        private void bindProduct(Product order) {

            mProduct = order;
            Log.d(TAG, "bindProduct: order name is :" + order.getName());
            mCard = mShoppingBagViewModel.getCart(order.getId());
            mProductCount = mCard.getProductCount();

            mTextViewName.setText(order.getName());
            mTextViewPrice.setText(order.getPrice()+
                    mContext.getResources().getString(R.string.toman));
            mTextViewCount.setText(mProductCount + "");
            setSumCardsPriceMutableLiveData();
           /* mBinding.textViewNameCardItem.setText(order.getName());
            mBinding.textViewPriceCardItem.setText(order.getPrice()+
                    mContext.getResources().getString(R.string.toman));

            mBinding.coutCardItem.setText(mProductCount + "");
            */
            checkVisibility();

            List<Image> imagesItems = order.getImages();
            if (imagesItems.get(0).getSrc().length() != 0)
                UIUtils.setImageUsingPicasso(imagesItems.get(0).getSrc(),
                       mImageViewCard);

        }

        private void setListener() {
            mTextViewPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCountAndPrice(++mProductCount);
                    Log.d(TAG, "count is " + mProductCount);
                    Log.d(TAG, "product name  is " + mProduct.getName());

                    checkVisibility();
                }
            });

            mTextViewMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCountAndPrice(--mProductCount);
                    Log.d(TAG, "count is " + mProductCount);
                    Log.d(TAG, "product name  is " + mProduct.getName());

                    checkVisibility();
                }
            });

            mImageViewTrash.setOnClickListener(new View.OnClickListener() {
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
                mTextViewMinus.setVisibility(View.VISIBLE);
                mImageViewTrash.setVisibility(View.GONE);

            } else {
                mTextViewMinus.setVisibility(View.GONE);
                mImageViewTrash.setVisibility(View.VISIBLE);

            }
        }

        private void updateCountAndPrice(int productCount) {
            mCard.setProductCount(productCount);
            mShoppingBagViewModel.updateCart(mCard);
            mTextViewCount.setText(productCount + "");
            setSumCardsPriceMutableLiveData();
        }

        private void setSumCardsPriceMutableLiveData() {
            mShoppingBagViewModel.setSumCardsPriceMutableLiveData();
        }

    }
}

