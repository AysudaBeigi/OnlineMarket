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
import com.example.onlinemarket.viewModel.SearchResultViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultProductsAdapter extends
        RecyclerView.Adapter<SearchResultProductsAdapter.ProductVerticalHolder> {

    private Context mContext;
    private List<Product> mProductsItem;
    private ProductVerticalItemViewBinding mBinding;
    private SearchResultViewModel mSearchResultViewModel;

    public void setProducts(List<Product> productsItem) {
        mProductsItem = productsItem;
        notifyDataSetChanged();
    }

    public SearchResultProductsAdapter(Context context, List<Product> products,
                                       ViewModelStoreOwner owner) {
        mContext = context;
        mProductsItem = products;
        mSearchResultViewModel=new ViewModelProvider(owner).get(SearchResultViewModel.class);
    }

    @NonNull
    @Override
    public ProductVerticalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding=
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.product_vertical_item_view, parent, false);

        return new ProductVerticalHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVerticalHolder holder, int position) {
        Product productItem = mProductsItem.get(position);
        holder.bindProduct(productItem);

    }

    @Override
    public int getItemCount() {
        return mProductsItem.size();
    }


    public class ProductVerticalHolder extends RecyclerView.ViewHolder {

        private Product mProduct;

        public ProductVerticalHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSearchResultViewModel.setUserSelectedProduct(mProduct);
                    NavController navController= Navigation.findNavController(itemView);
                    navController.navigate(
                            R.id.action_SearchResultFragment_to_productDetailFragment);
                }
            });

        }

        private void bindProduct(Product product) {
            mProduct=product;

            mBinding.setProduct(product);

           /* mBinding.textViewNameProductVerticalItem.setText(product.getName() + "");
            mBinding.textViewPriceProductVerticalItem.setText(product.getPrice() + " " +
                    mContext.getResources().getString(R.string.toman));
            */
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
