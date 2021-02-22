package com.example.onlinemarket.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.databinding.CommentItemViewBinding;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mComments;
    private CommentItemViewBinding mBinding;

    public void setComments(List<Comment> comments) {
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
        mBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.comment_item_view, parent, false);

        return new CommentViewHolder(mBinding.getRoot());
    }


    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.bindItem(comment);

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        private String getReview(Comment comment) {
            if (comment.getReview()== null)
                return null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(comment.getReview(),
                        Html.FROM_HTML_MODE_COMPACT).toString();
            } else {
                return Html.fromHtml(comment.getReview()).toString();
            }

        }

        private void bindItem(Comment comment) {

            mBinding.textViewCommentItem.setText(getReview(comment));
            int rate = comment.getRating();
            switch (rate) {
                case 1:
                    mBinding.radioButton1CommentItem.setChecked(true);
                    break;
                case 2:
                    mBinding.radioButton2CommentItem.setChecked(true);
                    break;
                case 3:
                    mBinding.radioButton3CommentItem.setChecked(true);
                    break;
                case 4:
                    mBinding.radioButton4CommentItem.setChecked(true);
                    break;
                case 5:
                    mBinding.radioButton5CommentItem.setChecked(true);
                    break;

            }
        }

    }

}
