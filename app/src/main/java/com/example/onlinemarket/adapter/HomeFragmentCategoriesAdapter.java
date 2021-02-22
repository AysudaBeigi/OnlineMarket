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
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.databinding.HomeFrgamentCategoryItemViewBinding;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.view.fragment.SubCategoryProductsFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentCategoriesAdapter extends RecyclerView.Adapter<HomeFragmentCategoriesAdapter.CategoryViewHolder> {
    private static final String TAG = "CategoryAdapter";
    private Context mContext;
    private List<Category> mCategoriesItems;
    private HomeFrgamentCategoryItemViewBinding mBinding;
    public void setCategoriesItem(List<Category> categoriesItems) {
        mCategoriesItems = categoriesItems;
        notifyDataSetChanged();
    }

    public HomeFragmentCategoriesAdapter(Context context, List<Category> categoriesItems) {
        mContext = context;
        mCategoriesItems = categoriesItems;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       mBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.home_frgament_category_item_view, parent, false);

        return new CategoryViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mCategoriesItems.get(position);
        holder.bindCategory(category, position);
    }

    @Override
    public int getItemCount() {
        return mCategoriesItems.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private Category mCategory;
        ArrayList<Integer> mColors = new ArrayList<>();

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            initColors();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController= Navigation.findNavController(itemView);
                    Bundle bundle=new Bundle();
                    bundle.putInt(SubCategoryProductsFragment.ARGS_SUBCATEGORY_ID,mCategory.getId());
                    navController.navigate(
                            R.id.action_HomeFragment_to_SubCategoryProductsFragment
                    ,bundle);
                }
            });

        }

        private void initColors() {
            mColors.add(mContext.getResources().getColor(R.color.digikala_green));
            mColors.add(mContext.getResources().getColor(R.color.digikala_red));
            mColors.add(mContext.getResources().getColor(R.color.digikala_blue));
            mColors.add(mContext.getResources().getColor(R.color.yellow_35));
            mColors.add(mContext.getResources().getColor(R.color.purple_47));
            mColors.add(mContext.getResources().getColor(R.color.orange_40));
        }


        private void bindCategory(Category category, int position) {
            mCategory=category;
            mBinding.cardViewHomeCategoriesItem.setCardBackgroundColor(mColors.get(position));
            mBinding.textViewCategoryNameHomeFragment.setText(category.getName() + "");
            Image imageItem = category.getImages();
            List<String> imagesItemList = new ArrayList<>();

            for (int i = 0; i < imageItem.getSrc().length(); i++) {
                imagesItemList.add(imageItem.getSrc());
            }

            for (int i = 0; i < imagesItemList.size(); i++) {
                if (imagesItemList.get(i) != null) {
                    UIUtils.setImageUsingPicasso(imagesItemList.get(i),
                            mBinding.imageViewCategoryHomeFragment);
                    break;
                }


            }

        }

    }

}
