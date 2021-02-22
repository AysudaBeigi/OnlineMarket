package com.example.onlinemarket.adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.view.fragment.ProductDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductsHorizontalAdapter extends RecyclerView.
        Adapter<CategoryProductsHorizontalAdapter.ProductHorizantalViewHolder> {

    private Context mContext;
    private List<Product> mProductsItem;
    private ProductHorizantalItemViewBinding mBinding;
    public void setProductsItem(List<Product> productsItem) {
        mProductsItem = productsItem;
        notifyDataSetChanged();
    }

    public CategoryProductsHorizontalAdapter(Context context, List<Product> productsItem) {
        mContext = context;
        mProductsItem = productsItem;
    }

    @NonNull
    @Override
    public ProductHorizantalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding=
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.product_horizantal__item_view, parent, false);

        return new ProductHorizantalViewHolder(mBinding.getRoot());
    }


    @Override
    public void onBindViewHolder(@NonNull ProductHorizantalViewHolder holder, int position) {
        Product productItem = mProductsItem.get(position);
        holder.bindProduct(productItem);

    }

    @Override
    public int getItemCount() {
        return mProductsItem.size();
    }


    public class ProductHorizantalViewHolder extends RecyclerView.ViewHolder {

        private Product mProduct;


        public ProductHorizantalViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController=Navigation.findNavController(itemView);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(ProductDetailFragment.ARGS_PRODUCT,mProduct);
                    navController.navigate(
                            R.id.action_CategoriesFragment_to_productDetailFragment
                    ,bundle);
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
