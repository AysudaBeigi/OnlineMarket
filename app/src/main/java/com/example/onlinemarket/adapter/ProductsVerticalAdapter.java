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
import com.example.onlinemarket.fragment.ProductDetailFragment;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.model.product.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsVerticalAdapter extends
        RecyclerView.Adapter<ProductsVerticalAdapter.ProductVerticalHolder> {

    private Context mContext;
    private List<Product> mProductsItem;

    public List<Product> getProductsItem() {
        return mProductsItem;
    }

    public void setProductsItem(List<Product> productsItem) {
        mProductsItem = productsItem;
        notifyDataSetChanged();
    }

    public ProductsVerticalAdapter(Context context, List<Product> productsItem) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((AppCompatActivity) mContext).
                                getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_main_activity,
                                        ProductDetailFragment.newInstance(productItem))
                                .commit();

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductsItem.size();
    }


    public class ProductVerticalHolder extends RecyclerView.ViewHolder {

        private TextView mName, mPrice;
        private ImageView mImage;
        private View mItemView;


        public ProductVerticalHolder(@NonNull View itemView) {
            super(itemView);
            findHolderViews(itemView);

        }


        private void findHolderViews(@NonNull View itemView) {
            mName = itemView.findViewById(R.id.product_category_holder_name);
            mPrice = itemView.findViewById(R.id.product_category_holder_price);
            mImage = itemView.findViewById(R.id.product_category_holder_image);

            mItemView = itemView;

        }

        private void bindProduct(Product productItem) {
            mName.setText(productItem.getName() + "");
            mPrice.setText(productItem.getPrice() + " " +
                    mContext.getResources().getString(R.string.toman));
            List<Image> productImagesList = productItem.getImages();
            List<String> productImageUrlsList = new ArrayList<>();
            for (int i = 0; i < productImagesList.size(); i++) {
                productImageUrlsList.add(productImagesList.get(i).getSrc());
            }
            for (int i = 0; i < productImageUrlsList.size(); i++) {
                if (productImageUrlsList.get(i) == null)
                    Picasso.get()
                            .load(R.drawable.ic_placeholder_recycler)
                            .placeholder(R.drawable.ic_placeholder_recycler)
                            .into(mImage);

                else
                    Picasso.get()
                            .load(productImageUrlsList.get(i))
                            .placeholder(R.drawable.ic_placeholder_recycler)
                            .into(mImage);
            }
        }

    }


}
