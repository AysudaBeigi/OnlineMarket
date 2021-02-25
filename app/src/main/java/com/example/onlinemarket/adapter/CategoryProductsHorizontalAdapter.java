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
import com.example.onlinemarket.databinding.ProductVerticalItemViewBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.CategoriesViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductsHorizontalAdapter extends RecyclerView.
        Adapter<CategoryProductsHorizontalAdapter.ProductHorizantalViewHolder> {
    public static String TAG = "OnlineMarket";

    private Context mContext;
    private List<Product> mProducts;
    private ProductVerticalItemViewBinding mBinding;
    private CategoriesViewModel mCategoriesViewModel;

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public CategoryProductsHorizontalAdapter(Context context, List<Product> products
            , ViewModelStoreOwner owner) {
        mContext = context;
        mProducts = products;
        mCategoriesViewModel = new ViewModelProvider(owner)
                .get(CategoriesViewModel.class);

    }

    @NonNull
    @Override
    public ProductHorizantalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.product_vertical_item_view, parent, false);

        return new ProductHorizantalViewHolder(mBinding.getRoot());
    }


    @Override
    public void onBindViewHolder(@NonNull ProductHorizantalViewHolder holder, int position) {
        Product product = mProducts.get(position);
        holder.bindProduct(product);

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    public class ProductHorizantalViewHolder extends RecyclerView.ViewHolder {

        private Product mProduct;

        public ProductHorizantalViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "CategoryProductsHorizontalAdapter : setOnClickListener");
                    Log.d(TAG, "CategoryProductsHorizontalAdapter : product name is "
                            +mProduct.getName());
                    Log.d(TAG, "CategoryProductsHorizontalAdapter : product price is "
                            +mProduct.getPrice());

                    mCategoriesViewModel.setUserSelectedProduct(mProduct);
                    NavController navController = Navigation.findNavController(itemView);
                    navController.navigate(
                            R.id.action_CategoriesFragment_to_productDetailFragment);
                }
            });
        }

        private void bindProduct(Product product) {
            Log.d(TAG, "CategoryProductsHorizontalAdapter : bindProduct");
            Log.d(TAG, "CategoryProductsHorizontalAdapter : product name is "+product.getName());
            Log.d(TAG, "CategoryProductsHorizontalAdapter : product price is "+product.getPrice());

            mProduct = product;
            mBinding.setProduct(product);

           /* mBinding.textViewNameProdcutHorizantalItem.setText(product.getName() + "");
            mBinding.textViewPriceProductHorizantalItem.setText(product.getPrice() + "");

            Log.d(TAG, "CategoryProductsHorizontalAdapter : product name in text view is  "
                    +mBinding.textViewNameProdcutHorizantalItem.getText());
            Log.d(TAG, "CategoryProductsHorizontalAdapter : product price in text view is  "
                    +mBinding.textViewPriceProductHorizantalItem.getText());
*/
            List<Image> imagesList = product.getImages();
            List<String> imagesSrcList = new ArrayList<>();
            for (int i = 0; i < imagesList.size(); i++) {
                imagesSrcList.add(imagesList.get(i).getSrc());
            }

            for (int i = 0; i < imagesSrcList.size(); i++) {
                if (imagesSrcList.get(i) != null) {
                    UIUtils.setImageUsingPicasso(imagesSrcList.get(i),
                            mBinding.imageViewProductVerticalItem);
                    break;

                }

            }


        }

    }

}
