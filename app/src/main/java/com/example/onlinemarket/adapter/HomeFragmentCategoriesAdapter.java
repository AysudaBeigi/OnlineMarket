package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.controller.fragment.SubCategoryProductsFragment;
import com.example.onlinemarket.model.product.Category;
import com.example.onlinemarket.model.product.Image;
import com.example.onlinemarket.utils.UIUtils;

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
                UIUtils.replaceFragment(
                        ((AppCompatActivity) mContext).getSupportFragmentManager(),
                        SubCategoryProductsFragment.newInstance(categoriesItem.getId()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoriesItems.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView mCategoryName;
        private ImageView mCategoryImage;
        private CardView mCardView;
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
            mCategoryName = itemView.findViewById(R.id.category_name);
            mCategoryImage = itemView.findViewById(R.id.category_image);
            mCardView = itemView.findViewById(R.id.card_view_home_categories_item);

        }

        private void bindCategory(Category categoriesItem, int position) {
            mCardView.setBackgroundColor(mColors.get(position));

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
