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
import com.example.onlinemarket.fragment.SubCategoryProductsFragment;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Image;
import com.squareup.picasso.Picasso;

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
        holder.bindCategory(categoriesItem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
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

        private TextView mCategoryName;
        private ImageView mCategoryImage;
        private View mItemView;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            findHolderViews(itemView);
        }


        private void findHolderViews(@NonNull View itemView) {
            mCategoryName = itemView.findViewById(R.id.category_name);
            mCategoryImage = itemView.findViewById(R.id.category_image);

            mItemView = itemView;

        }

        private void bindCategory(Category categoriesItem) {
            mCategoryName.setText(categoriesItem.getName() + "");
            Image imageItem = categoriesItem.getImages();
            List<String> imagesItemList = new ArrayList<>();

            for (int i = 0; i < imageItem.getSrc().length(); i++) {
                imagesItemList.add(imageItem.getSrc());
            }

            for (int i = 0; i < imagesItemList.size(); i++) {
                if(imagesItemList.get(i)!=null){

                    Picasso.get()
                            .load(imagesItemList.get(i))
                            .placeholder(R.drawable.ic_placeholder_recycler)
                            .into(mCategoryImage);
                    break;
                }


            }

        }

    }

}
