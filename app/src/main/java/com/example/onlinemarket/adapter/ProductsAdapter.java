package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.fragment.ProductDetailFragment;
import com.example.onlinemarket.model.Image;
import com.example.onlinemarket.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductsAdapter extends RecyclerView.
        Adapter<ProductsAdapter.ProductViewHolder>
        implements Filterable {

    private Context mContext;
    private List<Product> mProductsItem;
    private List<Product> mSearchProductsItem;

    public List<Product> getProductsItem() {
        return mProductsItem;
    }

    public void setProductsItem(List<Product> productsItem) {
        mProductsItem = productsItem;
        if (productsItem != null)
            this.mSearchProductsItem = new ArrayList<>(productsItem);
        notifyDataSetChanged();
    }

    public ProductsAdapter(Context context, List<Product> productsItem) {
        mContext = context;
        mProductsItem = productsItem;
        mSearchProductsItem = new ArrayList<>(productsItem);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_item_view, parent, false);

        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product productItem = mProductsItem.get(position);
        holder.bindProduct(productItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) mContext).
                        getSupportFragmentManager().
                        beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
                                ProductDetailFragment.newInstance(productItem))
                        .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductsItem.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Product> filteredList = new ArrayList<>();

                if (charSequence.toString().isEmpty()) {
                    filteredList.addAll(mSearchProductsItem);
                } else {
                    for (Product productsItem : mSearchProductsItem) {
                        if (productsItem.getName().toLowerCase().trim().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredList.add(productsItem);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                if (mProductsItem != null)
                    mProductsItem.clear();
                if (filterResults.values != null)
                    mProductsItem.
                            addAll((Collection<? extends Product>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView mProductName;
        private TextView mProductPrice;
        private ImageView mProductImage;


        public ProductViewHolder(@NonNull View itemView) {
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
            List<String> imagesUrlList = new ArrayList<>();
            for (int i = 0; i < imagesList.size(); i++) {
                imagesUrlList.add(imagesList.get(i).getSrc());
            }

            for (int i = 0; i < imagesUrlList.size(); i++) {
                if (imagesUrlList.get(i) != null) {
                    Picasso.get()
                            .load(imagesUrlList.get(i))
                            .placeholder(R.drawable.ic_placeholder_recycler)
                            .into(mProductImage);

                }
            }


        }

    }

}
