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
import com.example.onlinemarket.controller.fragment.ProductDetailFragment;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.example.onlinemarket.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductsHorizontalAdapter extends RecyclerView.
        Adapter<ProductsHorizontalAdapter.ProductHorizantalViewHolder> {

    private Context mContext;
    private List<Product> mProductsItem;

    public List<Product> getProductsItem() {
        return mProductsItem;
    }

    public void setProductsItem(List<Product> productsItem) {
        mProductsItem = productsItem;
        notifyDataSetChanged();
    }

    public ProductsHorizontalAdapter(Context context, List<Product> productsItem) {
        mContext = context;
        mProductsItem = productsItem;
    }

    @NonNull
    @Override
    public ProductHorizantalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_horizantal__item_view, parent, false);

        return new ProductHorizantalViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductHorizantalViewHolder holder, int position) {
        Product productItem = mProductsItem.get(position);
        holder.bindProduct(productItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UIUtils.replaceFragment(((AppCompatActivity) mContext).
                        getSupportFragmentManager(),ProductDetailFragment.
                        newInstance(productItem));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductsItem.size();
    }


    public class ProductHorizantalViewHolder extends RecyclerView.ViewHolder {

        private TextView mProductName;
        private TextView mProductPrice;
        private ImageView mProductImage;


        public ProductHorizantalViewHolder(@NonNull View itemView) {
            super(itemView);

            findHolderViews(itemView);

        }


        private void findHolderViews(@NonNull View itemView) {
            mProductName = itemView.findViewById(R.id.product_view_holder_title);
            mProductPrice = itemView.findViewById(R.id.product_view_holder_price);
            mProductImage = itemView.findViewById(R.id.product_view_holder_image_cover);

        }

        private void bindProduct(Product productItem) {
            mProductName.setText(productItem.getName() + "");
            mProductPrice.setText(productItem.getPrice() + "");
            List<Image> imagesList = productItem.getImages();
            List<String> imagesSrclList = new ArrayList<>();
            for (int i = 0; i < imagesList.size(); i++) {
                imagesSrclList.add(imagesList.get(i).getSrc());
            }

            for (int i = 0; i < imagesSrclList.size(); i++) {
                if (imagesSrclList.get(i) != null) {
                    UIUtils.setImageUsingPicasso(imagesSrclList.get(i),mProductImage);
                      break;

                }

            }


        }

    }

}
