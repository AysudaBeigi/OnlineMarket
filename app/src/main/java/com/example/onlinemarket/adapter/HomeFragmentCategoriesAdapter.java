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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentCategoriesAdapter extends RecyclerView.Adapter<HomeFragmentCategoriesAdapter.CategoryViewHolder> {
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

    public HomeFragmentCategoriesAdapter(Context context, List<Category> categoriesItems) {
        mContext = context;
        mCategoriesItems = categoriesItems;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.home_frgament_category_item_view, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category categoriesItem = mCategoriesItems.get(position);
        holder.bindCategory(categoriesItem, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        ((AppCompatActivity) mContext).getSupportFragmentManager()
                                .beginTransaction().
                                replace(R.id.fragment_container_main_activity,
                        SubCategoryProductsFragment.newInstance(categoriesItem.getId()))
                .commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoriesItems.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mCategoryName;
        private ShapeableImageView mCategoryImage;
        private MaterialCardView mCardView;
        ArrayList<Integer> mColors = new ArrayList<>();

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            findItemViews(itemView);
            initColors();

        }

        private void initColors() {
            mColors.add(mContext.getResources().getColor(R.color.digikala_green));
            mColors.add(mContext.getResources().getColor(R.color.digikala_red));
            mColors.add(mContext.getResources().getColor(R.color.digikala_blue));
            mColors.add(mContext.getResources().getColor(R.color.yellow_35));
            mColors.add(mContext.getResources().getColor(R.color.purple_47));
            mColors.add(mContext.getResources().getColor(R.color.orange_40));
        }


        private void findItemViews(@NonNull View itemView) {
            mCategoryName = itemView.findViewById(R.id.text_view_category_name_home_fragment);
            mCategoryImage = itemView.findViewById(R.id.image_view_category_home_fragment);
            mCardView = itemView.findViewById(R.id.card_view_home_categories_item);

        }

        private void bindCategory(Category categoriesItem, int position) {
            mCardView.setCardBackgroundColor(mColors.get(position));
            mCategoryName.setText(categoriesItem.getName() + "");
            Image imageItem = categoriesItem.getImages();
            List<String> imagesItemList = new ArrayList<>();

            for (int i = 0; i < imageItem.getSrc().length(); i++) {
                imagesItemList.add(imageItem.getSrc());
            }

            for (int i = 0; i < imagesItemList.size(); i++) {
                if (imagesItemList.get(i) != null) {
                    UIUtils.setImageUsingPicasso(imagesItemList.get(i),
                            mCategoryImage);
                    break;
                }


            }

        }

    }

}
