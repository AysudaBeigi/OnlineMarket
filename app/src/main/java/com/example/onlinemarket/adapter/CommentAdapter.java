package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.databinding.CommentItemViewBinding;
import com.example.onlinemarket.viewModel.PostCommentViewModel;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mComments;
    private CommentItemViewBinding mBinding;
    private PostCommentViewModel mPostCommentViewModel;

    public void setComments(List<Comment> comments) {
        mComments = comments;
        notifyDataSetChanged();
    }

    public CommentAdapter(Context context, List<Comment> comments, ViewModelStoreOwner owner) {
        mContext = context;
        mComments = comments;
        mPostCommentViewModel=new ViewModelProvider(owner)
                .get(PostCommentViewModel.class);
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


        private void bindItem(Comment comment) {

            mBinding.setComment(comment);
            mBinding.setPostCommentViewModel(mPostCommentViewModel);
           /* mBinding.textViewCommentItem.setText(getReview(comment));
           */
           /* int rate = comment.getRating();
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

            }*/
        }

    }

}
