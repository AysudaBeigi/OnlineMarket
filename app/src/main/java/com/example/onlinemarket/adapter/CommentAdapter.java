package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Comment;
import com.example.onlinemarket.model.product.Product;

import java.util.List;

public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mComments;

    public List<Comment> getProductsItem() {
        return mComments;
    }

    public void setProductsItem(List<Comment> comments) {
        mComments = comments;
        notifyDataSetChanged();
    }

    public CommentAdapter(Context context, List<Comment> comments) {
        mContext = context;
        mComments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_horizantal__item_view, parent, false);

        return new CommentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
       // holder.bindProduct(review);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*((AppCompatActivity) mContext).
                        getSupportFragmentManager().
                        beginTransaction()
                        .replace(R.id.fragment_container_main_activity,
                                ProductDetailFragment.newInstance(review))
                        .commit();
*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView mProductName;
        private TextView mProductPrice;
        private ImageView mProductImage;


        public CommentViewHolder(@NonNull View itemView) {
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


        }

    }

}
