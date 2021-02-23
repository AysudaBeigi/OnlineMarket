package com.example.onlinemarket.adapter;

import android.content.Context;
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
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.databinding.ProductVerticalItemViewBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.SubCategoryProductsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryProductsAdapter extends
            RecyclerView.Adapter<SubCategoryProductsAdapter.SubCategoryProductsViewHolder> {

        private Context mContext;
        private List<Product> mProducts;
        private ProductVerticalItemViewBinding mBinding;
        private SubCategoryProductsViewModel mSubCategoryProductsViewModel;
        public void setProducts(List<Product> products) {
            mProducts = products;
            notifyDataSetChanged();
        }

        public SubCategoryProductsAdapter(Context context, List<Product> products,
                                          ViewModelStoreOwner owner) {
            mContext = context;
            mProducts = products;
            mSubCategoryProductsViewModel=new ViewModelProvider(owner).
                    get(SubCategoryProductsViewModel.class);
        }

        @NonNull
        @Override
        public SubCategoryProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                int viewType) {
            mBinding =
                   DataBindingUtil.inflate( LayoutInflater.from(mContext),
                    R.layout.product_vertical_item_view, parent, false);

            return new SubCategoryProductsViewHolder(mBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull SubCategoryProductsViewHolder holder, int position) {
            Product productItem = mProducts.get(position);
            holder.bindProduct(productItem);

        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }


        public class SubCategoryProductsViewHolder extends RecyclerView.ViewHolder {

            private Product mProduct;

            public SubCategoryProductsViewHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSubCategoryProductsViewModel.setUserSelectedProduct(mProduct);
                        NavController navController= Navigation.findNavController(itemView);
                        navController.navigate(
                                R.id.action_SubCategoryProductsFragment_to_productDetailFragment);
                    }
                });

            }

            private void bindProduct(Product product) {
                mProduct=product;
                mBinding.textViewNameProductVerticalItem.setText(product.getName() + "");
                mBinding.textViewPriceProductVerticalItem.setText(product.getPrice() + " " +
                        mContext.getResources().getString(R.string.toman));
                List<Image> productImagesList = product.getImages();
                List<String> productImagesSrcList = new ArrayList<>();
                for (int i = 0; i < productImagesList.size(); i++) {
                    productImagesSrcList.add(productImagesList.get(i).getSrc());
                }
                for (int i = 0; i < productImagesSrcList.size(); i++) {

                    UIUtils.setImageUsingPicasso(productImagesSrcList.get(i),
                            mBinding.imageViewProductVerticalItem);
                    break;
                }
            }

        }


    }


