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
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.databinding.CategoriesFragmentItemViewBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.viewModel.SubCategoryProductsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragmentAdapter extends
        RecyclerView.Adapter<CategoriesFragmentAdapter.CategoriesFragmentViewHolder> {


    private static final String TAG = "CategoryAdapter";
    private Context mContext;
    private List<Category> mCategories;
    private SubCategoryProductsViewModel mSubCategoryProductsViewModel;
    private CategoriesFragmentItemViewBinding mBinding;


    public void setCategoriesItem(List<Category> categoriesItems) {
        mCategories = categoriesItems;
        notifyDataSetChanged();
    }

    public CategoriesFragmentAdapter(Context context, List<Category> categories
    ,  ViewModelStoreOwner viewModelStoreOwner) {
        mContext = context;
        mCategories = categories;
        mSubCategoryProductsViewModel = new ViewModelProvider(viewModelStoreOwner)
                .get(SubCategoryProductsViewModel.class);
    }

    @NonNull
    @Override
    public CategoriesFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.categories_fragment_item_view,
                        parent,
                        false);

        return new CategoriesFragmentViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesFragmentViewHolder holder,
                                 int position) {
        Category category = mCategories.get(position);
        holder.bindCategory(category);

    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }


    public class CategoriesFragmentViewHolder extends RecyclerView.ViewHolder {

        private Category mCategory;

        public CategoriesFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSubCategoryProductsViewModel.setCategoryProductsLiveData(mCategory.getId());
                    NavController navController = Navigation.findNavController(itemView);
                    navController.
                            navigate(R.id.
                                    action_CategoriesFragment_to_SubCategoryProductsFragment);

                }
            });

        }

        private void bindCategory(Category category) {
            mCategory = category;
            mBinding.textViewNameCategoriesItem.setText(category.getName() + "");
            Image imageItem = category.getImages();
            List<String> imagesItemList = new ArrayList<>();

            for (int i = 0; i < imageItem.getSrc().length(); i++) {
                imagesItemList.add(imageItem.getSrc());
            }

            for (int i = 0; i < imagesItemList.size(); i++) {
                if (imagesItemList.get(i) != null) {

                    UIUtils.setImageUsingPicasso(imagesItemList.get(i),
                            mBinding.imageViewCategoriesItem);
                    break;
                }

            }

        }

    }

}

