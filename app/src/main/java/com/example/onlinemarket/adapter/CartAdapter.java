package com.example.onlinemarket.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.controller.fragment.ShoppingBagFragment;
import com.example.onlinemarket.model.Cart;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.repository.CartDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CardViewHolder> {

    public static final String TAG = "CardAdapter";
    private Context mContext;
    private List<Product> mCartProducts;
    private int mFinalPriceValue = 0;
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

        private TextView mName, mSalePrice;
        private MaterialButton mMaterialButtonPlus,
                mMaterialButtonMinus, mMaterialButtonDelete;
        private MaterialTextView mCount, mFinalPrice;
        private ImageView mImageView;
        private Cart mCart;
        private int mProductCount;
        private int basePrice = 0;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            findHolderViews(itemView);
        }


        private void findHolderViews(@NonNull View itemView) {
            mName = itemView.findViewById(R.id.card_name);
            mMaterialButtonPlus = itemView.findViewById(R.id.card_plus_button);
            mMaterialButtonMinus = itemView.findViewById(R.id.card_minus_button);
            mMaterialButtonDelete = itemView.findViewById(R.id.card_delete);
            mCount = itemView.findViewById(R.id.card_count);
            mImageView = itemView.findViewById(R.id.card_image_view);
            mFinalPrice = itemView.findViewById(R.id.card_final_price);

        }

        private void bindProduct(Product product) {
            mCart=mCartDBRepository.getCart(product.getId());
            mProductCount=mCart.getProductCount();
            mName.setText(product.getName() + "");
            mFinalPrice.setText(product.getPrice() +
                    " " + mContext.getResources().getString(R.string.toman));
            mCount.setText(mProductCount + "");
            basePrice = Integer.parseInt(product.getPrice());
            mSalePrice.setText(mProductCount * Integer.parseInt(product.getPrice())
                    + " " + mContext.getResources().getString(R.string.toman));

            mFinalPriceValue = mFinalPriceValue + mProductCount * basePrice;

            List<Image> imagesItems = product.getImages();
            if (imagesItems.get(0).getSrc().length() != 0)
                Picasso.get()
                        .load(imagesItems.get(0).getSrc())
                        .into(mImageView);

        }

        private void setListener(int position) {
            mMaterialButtonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCart.setProductCount(++mProductCount);
                    mCartDBRepository.updateCart(mCart);
                    if (mProductCount <= 0)
                        mProductCount = 0;
                    mProductCount++;

                    if (mProductCount >= 0) {
                        mCount.setText(mProductCount + "");
                        mSalePrice.setText(mProductCount * basePrice +
                                " " + mContext.getResources().getString(R.string.toman));
                        mFinalPriceValue = mFinalPriceValue + mProductCount * basePrice;

                    }

                }
            });

            mMaterialButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProductCount--;
                    if (mProductCount >= 0) {
                        mCount.setText(mProductCount + "");
                        mSalePrice.setText(mProductCount * basePrice +
                                " " + mContext.getResources().getString(R.string.toman));
                        mFinalPriceValue = mFinalPriceValue - mProductCount * basePrice;
                    }
                }
            });

            mMaterialButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart = mCartDBRepository.getCart(mCartProducts.get(position).getId());
                    mCartDBRepository.deleteCart(cart);
                    ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_main_activity,
                                    ShoppingBagFragment.newInstance())
                            .commit();


                }
            });

        }

    }
}

