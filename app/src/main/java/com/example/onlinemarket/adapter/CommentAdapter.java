package com.example.onlinemarket.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Comment;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mComments;

    public List<Comment> getComments() {
        return mComments;
    }

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
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.comment_item_view, parent, false);

        return new CommentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.bindItem(comment);

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
        private RadioButton mRadioButton1;
        private RadioButton mRadioButton2;
        private RadioButton mRadioButton3;
        private RadioButton mRadioButton4;
        private RadioButton mRadioButton5;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            findItemViews(itemView);

        }


        private void findItemViews(@NonNull View itemView) {
            mTextViewComment = itemView.findViewById(R.id.text_view_comment_item);
            mRadioButton1 = itemView.findViewById(R.id.radio_button_1_comment_item);
            mRadioButton2 = itemView.findViewById(R.id.radio_button_2_comment_item);
            mRadioButton3 = itemView.findViewById(R.id.radio_button_3_comment_item);
            mRadioButton4 = itemView.findViewById(R.id.radio_button_4_comment_item);
            mRadioButton5 = itemView.findViewById(R.id.radio_button_5_comment_item);
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

            mTextViewComment.setText(getReview(comment));
            int rate = comment.getRating();
            switch (rate) {
                case 1:
                    mRadioButton1.setChecked(true);
                    break;
                case 2:
                    mRadioButton2.setChecked(true);
                    break;
                case 3:
                    mRadioButton3.setChecked(true);
                    break;
                case 4:
                    mRadioButton4.setChecked(true);
                    break;
                case 5:
                    mRadioButton5.setChecked(true);
                    break;

            }
        }

    }

}
