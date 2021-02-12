package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Comment;
import com.google.android.material.textview.MaterialTextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

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

        private String getDescription(Comment comment) {
            String description = comment.getReview();
            if (description.equals(null))
                return description;
            Document document = Jsoup.parse(description);
            document.outputSettings(new Document.OutputSettings().prettyPrint(false));
            document.select("br").append("\\n");
            document.select("p").prepend("\\n\\n");
            String s = document.html().replaceAll("\\\\n", "\n");
            return Jsoup.clean(s, "", Whitelist.none(),
                    new Document.OutputSettings().prettyPrint(false));


        }

        private void bindItem(Comment comment) {

            mTextViewComment.setText(getDescription(comment));
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
