package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.controller.fragment.SubCategoryProductsFragment;
import com.example.onlinemarket.model.product.Category;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.utils.UIUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragmentAdapter extends
        RecyclerView.Adapter<CategoriesFragmentAdapter.CategoriesFragmentViewHolder> {


    private static final String TAG = "CategoryAdapter";
    private Context mContext;
    private List<Category> mCategoriesItems;

    public List<Category> getCategoriesItem() {
        return mCategoriesItems;

    }

    public void setCategoriesItem(List<Category> categoriesItems) {
        mCategoriesItems = categoriesItems;
        notifyDataSetChanged();
    }

    public CategoriesFragmentAdapter(Context context, List<Category> categoriesItems) {
        mContext = context;
        mCategoriesItems = categoriesItems;
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
        Category categoriesItem = mCategoriesItems.get(position);
        holder.bindCategory(categoriesItem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) mContext).
                        getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
                                SubCategoryProductsFragment.
                                newInstance(categoriesItem.getId())).
                        commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoriesItems.size();
    }


    public class CategoriesFragmentViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mCategoryName;
        private ShapeableImageView mCategoryImage;


        public CategoriesFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            findHolderViews(itemView);
        }


        private void findHolderViews(@NonNull View itemView) {
            mCategoryName = itemView.findViewById(R.id.text_view_name_categories_item);
            mCategoryImage = itemView.findViewById(R.id.image_view_categories_item);

        }

        private void bindCategory(Category categoriesItem) {
            mCategoryName.setText(categoriesItem.getName() + "");
            Image imageItem = categoriesItem.getImages();
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

