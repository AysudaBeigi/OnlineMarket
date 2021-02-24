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
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.databinding.ProductHorizantalItemViewBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeProductsAdapter extends RecyclerView.
        Adapter<HomeProductsAdapter.HomeProductsHorizantalViewHolder> {

    private Context mContext;
    private List<Product> mProduct;
    private ProductHorizantalItemViewBinding mBinding;
    private ViewModelStoreOwner mOwner;
    public static String TAG = "OnlineMarket";

    public void setProducts(List<Product> product) {
        Log.d(TAG, "HomeProductsAdapter : setProduct");

        mProduct = product;
        notifyDataSetChanged();
    }

    public HomeProductsAdapter(Context context, List<Product> product,
                               ViewModelStoreOwner owner) {
        Log.d(TAG, "HomeProductsAdapter : HomeProductsAdapter");

        mContext = context;
        mProduct = product;
        mOwner=owner;
    }

    @NonNull
    @Override
    public HomeProductsHorizantalViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        Log.d(TAG, "HomeProductsAdapter : onCreateViewHolder");

        mBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.product_horizantal__item_view, parent,
                        false);


        return new HomeProductsHorizantalViewHolder(mBinding.getRoot());

    }


    @Override
    public void onBindViewHolder(@NonNull HomeProductsHorizantalViewHolder holder,
                                 int position) {
        Log.d(TAG, "HomeProductsAdapter : onBindViewHolder");

        Product product = mProduct.get(position);
        holder.bindProduct(product);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "HomeProductsAdapter : getItemCount");

        return mProduct.size();
    }


    public class HomeProductsHorizantalViewHolder extends RecyclerView.ViewHolder {

        private Product mProduct;

        public HomeProductsHorizantalViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "HomeProductsAdapter : HomeProductsHorizantalViewHolder");


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, " HomeProductsHorizantalViewHolder: onClick ");

                    Log.d(TAG, "HomeProductsAdapter : setOnClickListener");
                    Log.d(TAG, "HomeProductsAdapter : product name is "
                            +mProduct.getName());
                    Log.d(TAG, "HomeProductsAdapter : product price is "
                            +mProduct.getPrice());

                    HomeViewModel viewModel
                            = new ViewModelProvider(mOwner).get(HomeViewModel.class);
                    viewModel.setUserSelectedProduct(mProduct);

                    NavController navController = Navigation.findNavController(itemView);
                    navController.navigate(
                            R.id.action_HomeFragment_to_productDetailFragment);
                }
            });
        }


        private void bindProduct(Product product) {
            Log.d(TAG, "HomeProductsAdapter : bindProduct");
            Log.d(TAG, "HomeProductsAdapter :name is "+product.getName());
            Log.d(TAG, "HomeProductsAdapter : price is "+product.getPrice());

            mProduct = product;
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

