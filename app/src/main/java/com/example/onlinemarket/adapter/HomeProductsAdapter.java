package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.databinding.ProductHorizantalItemViewBinding;
import com.example.onlinemarket.utils.OnlineMarketPreferences;
import com.example.onlinemarket.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeProductsAdapter extends RecyclerView.
        Adapter<HomeProductsAdapter.HomeProductsHorizantalViewHolder> {

private Context mContext;
        private List<Product> mProduct;
        private ProductHorizantalItemViewBinding mBinding;

        public void setProduct(List<Product> product) {
            mProduct = product;
            notifyDataSetChanged();
        }

        public HomeProductsAdapter(Context context, List<Product> product) {
            mContext = context;
            mProduct = product;
        }

        @NonNull
        @Override
        public HomeProductsHorizantalViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType) {
           mBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(mContext),
                    R.layout.product_horizantal__item_view, parent,
                            false);

            return new HomeProductsHorizantalViewHolder(
                    mBinding.getRoot());

        }


        @Override
        public void onBindViewHolder(@NonNull HomeProductsHorizantalViewHolder holder,
                                     int position) {
            Product product = mProduct.get(position);
            holder.bindProduct(product);
        }

        @Override
        public int getItemCount() {
            return mProduct.size();
        }


        public class HomeProductsHorizantalViewHolder extends RecyclerView.ViewHolder {

            private Product mProduct;

            public HomeProductsHorizantalViewHolder(@NonNull View itemView) {
                super(itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavController navController= Navigation.findNavController(itemView);
                        OnlineMarketPreferences.
                                getInstance(mContext).
                                setUserSelectedProduct(mProduct);
                        navController.navigate(
                                R.id.action_HomeFragment_to_productDetailFragment);
                    }
                });
            }


            private void bindProduct(Product product) {
                mProduct=product;
                mBinding.textViewNameProdcutHorizantalItem.setText(product.getName() + "");
                mBinding.textViewPriceProductHorizantalItem.setText(product.getPrice() + "");
                List<Image> imagesList = product.getImages();
                List<String> imagesSrclList = new ArrayList<>();
                for (int i = 0; i < imagesList.size(); i++) {
                    imagesSrclList.add(imagesList.get(i).getSrc());
                }

                for (int i = 0; i < imagesSrclList.size(); i++) {
                    if (imagesSrclList.get(i) != null) {
                        UIUtils.setImageUsingPicasso(imagesSrclList.get(i),
                                mBinding.imageViewProdcutHorizantalItem);
                        break;

                    }

                }


            }

        }

    }

