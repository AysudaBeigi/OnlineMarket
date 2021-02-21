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
import com.example.onlinemarket.data.model.product.Category;
import com.example.onlinemarket.data.model.product.Image;
import com.example.onlinemarket.utils.UIUtils;
import com.example.onlinemarket.view.fragment.SubCategoryProductsFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragmentAdapter extends
        RecyclerView.Adapter<CategoriesFragmentAdapter.CategoriesFragmentViewHolder> {


    private static final String TAG = "CategoryAdapter";
    private Context mContext;
    private List<Category> mCategories;

    public List<Category> getCategoriesItem() {
        return mCategories;

    }

    public void setCategoriesItem(List<Category> categoriesItems) {
        mCategories = categoriesItems;
        notifyDataSetChanged();
    }

    public CategoriesFragmentAdapter(Context context, List<Category> categories) {
        mContext = context;
        mCategories = categories;
    }

    @NonNull
    @Override
    public CategoriesFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.categories_fragment_item_view,
                        parent,
                        false);

        return new CategoriesFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesFragmentViewHolder holder,
                                 int position) {
        Category category = mCategories.get(position);
        holder.bindCategory(category);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) mContext).
                        getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
                                SubCategoryProductsFragment.
                                newInstance(categoriesItem.getId())).
                        commit();


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }


    public class CategoriesFragmentViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mCategoryName;
        private ShapeableImageView mCategoryImage;
        private Category mCategory;

        public CategoriesFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            findHolderViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController(itemView);
                    Bundle bundle=new Bundle();
                    bundle.
                            putInt(SubCategoryProductsFragment.ARGS_SUBCATEGORY_ID,mCategory.getId());
                    navController.
                            navigate(R.id.
                                    action_CategoriesFragment_to_SubCategoryProductsFragment
                            ,bundle);

                }
            });

        }


        private void findHolderViews(@NonNull View itemView) {
            mCategoryName = itemView.findViewById(R.id.text_view_name_categories_item);
            mCategoryImage = itemView.findViewById(R.id.image_view_categories_item);

        }

        private void bindCategory(Category category) {
            mCategory=category;
            mCategoryName.setText(category.getName() + "");
            Image imageItem = category.getImages();
            List<String> imagesItemList = new ArrayList<>();

            for (int i = 0; i < imageItem.getSrc().length(); i++) {
                imagesItemList.add(imageItem.getSrc());
            }

            for (int i = 0; i < imagesItemList.size(); i++) {
                if (imagesItemList.get(i) != null) {

                    UIUtils.setImageUsingPicasso(imagesItemList.get(i), mCategoryImage);
                    break;
                }

            }

        }

    }

}

