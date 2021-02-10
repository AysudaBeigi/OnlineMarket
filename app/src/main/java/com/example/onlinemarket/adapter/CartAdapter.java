package com.example.onlinemarket.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.controller.fragment.ShoppingBagFragment;
import com.example.onlinemarket.model.Cart;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CartDBRepository;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CardViewHolder> {

    public static final String TAG = "CardAdapter";
    private Context mContext;
    private List<Product> mCartProducts;
    private int mSumPriceCarts = 0;
    CartDBRepository mCartDBRepository;


    public List<Product> getCartProducts() {
        return mCartProducts;
    }

    public void setCartProducts(List<Product> cartProducts) {
        mCartProducts = cartProducts;
        notifyDataSetChanged();
    }

    public CartAdapter(Context context, List<Product> cartProducts) {
        mContext = context;
        mCartProducts = cartProducts;
        mCartDBRepository = CartDBRepository.getInstance(mContext);
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
        Product productItem = mCartProducts.get(position);
        holder.bindProduct(productItem);
        holder.setListener(position);
    }

    @Override
    public int getItemCount() {
        return mCartProducts.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mTextViewName;
        private MaterialTextView mTextViewPlus,
                mTextViewMinus;
        private MaterialTextView mTextViewCount, mTextViewBasePriceCart;
        private ShapeableImageView mImageViewProduct, mImageViewTrash;
        private Cart mCart;
        private int mProductCount;
        private int basePriceCart = 0;
        private int mSumPriceCart = 0;


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
            mTextViewBasePriceCart = itemView.findViewById(R.id.text_view_product_price);

        }

        private void bindProduct(Product product) {
            mCart = mCartDBRepository.getCart(product.getId());
            mProductCount = mCart.getProductCount();
            mTextViewName.setText(product.getName());
            basePriceCart = Integer.parseInt(product.getPrice());
            mTextViewBasePriceCart.setText(basePriceCart
                    + mContext.getResources().getString(R.string.toman));
            mTextViewCount.setText(mProductCount);
            mSumPriceCart = basePriceCart * mProductCount;
            mSumPriceCarts = mSumPriceCarts + mSumPriceCart;

            List<Image> imagesItems = product.getImages();
            if (imagesItems.get(0).getSrc().length() != 0)
                Picasso.get()
                        .load(imagesItems.get(0).getSrc())
                        .into(mImageViewProduct);

        }

        private void setListener(int position) {
            mTextViewPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCart.setProductCount(++mProductCount);
                    mCartDBRepository.updateCart(mCart);
                    mTextViewCount.setText(mProductCount + "");
                    updateSumPriceCarts();

                }
            });

            mTextViewMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProductCount--;
                    if (mProductCount > 0) {
                        mTextViewCount.setText(mProductCount + "");
                        updateSumPriceCarts();
                    }else {
                        mTextViewMinus.setVisibility(View.GONE);
                        mImageViewTrash.setVisibility(View.VISIBLE);
                    }
                }
            });

            mImageViewTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart = mCartDBRepository.
                            getCart(mCartProducts.get(position).getId());
                    mCartDBRepository.deleteCart(cart);
                    ((AppCompatActivity) mContext).getSupportFragmentManager().
                            beginTransaction()
                            .replace(R.id.fragment_container_main_activity,
                                    ShoppingBagFragment.newInstance())
                            .commit();


                }
            });

        }

        private void updateSumPriceCarts() {
            mSumPriceCart = mProductCount * basePriceCart;
            mSumPriceCarts = mSumPriceCarts + mSumPriceCart;
        }

    }
}

