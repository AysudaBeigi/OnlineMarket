package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Comment;
import com.example.onlinemarket.model.product.Product;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

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
                .inflate(R.layout.comment_item_view, parent, false);

        return new CommentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.bindProduct(comment);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView mTextViewComment;
        private RadioGroup mRadioGroupRating;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            findItemViews(itemView);

        }


        private void findItemViews(@NonNull View itemView) {
            mTextViewComment = itemView.findViewById(R.id.text_view_comment_item);
            mRadioGroupRating = itemView.findViewById(R.id.radio_group_rating_comment_item);

        }

        private void bindProduct(Product productItem) {
            mProductName.setText(productItem.getName() + "");
            mProductPrice.setText(productItem.getPrice() + "");


        }

    }

}
