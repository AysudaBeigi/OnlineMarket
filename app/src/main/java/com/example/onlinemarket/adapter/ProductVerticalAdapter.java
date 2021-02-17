package com.example.onlinemarket.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.controller.fragment.ProductDetailFragment;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class ProductVerticalAdapter extends
        RecyclerView.Adapter<ProductVerticalAdapter.ProductVerticalHolder> {

    private Context mContext;
    private List<Product> mProductsItem;

    public List<Product> getProductsItem() {
        return mProductsItem;
    }

    public void setProductsItem(List<Product> productsItem) {
        mProductsItem = productsItem;
        notifyDataSetChanged();
    }

    public ProductVerticalAdapter(Context context, List<Product> productsItem) {
        mContext = context;
        mProductsItem = productsItem;
    }

    @NonNull
    @Override
    public ProductVerticalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_vertical_item_view, parent, false);

        return new ProductVerticalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVerticalHolder holder, int position) {
        Product productItem = mProductsItem.get(position);
        holder.bindProduct(productItem);
/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((AppCompatActivity) mContext).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_main_activity,
                                        ProductDetailFragment.newInstance(productItem)).
                                commit();

                    }
                });

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mProductsItem.size();
    }


    public class ProductVerticalHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mName, mPrice;
        private ShapeableImageView mImage;
        private Product mProduct;

        public ProductVerticalHolder(@NonNull View itemView) {
            super(itemView);
            findHolderViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController= Navigation.findNavController(itemView);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(ProductDetailFragment.ARGS_PRODUCT,mProduct);
                    navController.navigate(
                            R.id.action_SubCategoryProductsFragment_to_productDetailFragment
                    , bundle);
                }
            });

        }


        private void findHolderViews(@NonNull View itemView) {
            mName = itemView.findViewById(R.id.text_view_name_product_vertical_item);
            mPrice = itemView.findViewById(R.id.text_view_price_product_vertical_item);
            mImage = itemView.findViewById(R.id.image_view_product_vertical_item);

        }

        private void bindProduct(Product product) {
            mProduct=product;
            mName.setText(product.getName() + "");
            mPrice.setText(product.getPrice() + " " +
                    mContext.getResources().getString(R.string.toman));
            List<Image> productImagesList = product.getImages();
            List<String> productImagesSrcList = new ArrayList<>();
            for (int i = 0; i < productImagesList.size(); i++) {
                productImagesSrcList.add(productImagesList.get(i).getSrc());
            }
            for (int i = 0; i < productImagesSrcList.size(); i++) {

                UIUtils.setImageUsingPicasso(productImagesSrcList.get(i), mImage);
                break;
            }
        }

    }


}
