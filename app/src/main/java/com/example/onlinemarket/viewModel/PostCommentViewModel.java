package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.os.Build;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.data.repository.CommentRepository;
import com.example.onlinemarket.data.repository.CustomerDBRepository;
import com.example.onlinemarket.data.repository.ProductRepository;

public class PostCommentViewModel extends AndroidViewModel {
    private CustomerDBRepository mCustomerDBRepository;
    private CommentRepository mCommentRepository;
    private ProductRepository mProductRepository;

    public PostCommentViewModel(@NonNull Application application) {
        super(application);
        mCustomerDBRepository=CustomerDBRepository.getInstance(application);
        mCommentRepository= CommentRepository.getInstance(application);
        mProductRepository=ProductRepository.getInstance(application);

    }

    public MutableLiveData<Comment> getPostCommentLiveData(){
       return   mCommentRepository.getPostCommentLiveData();
    }
     public void postCommentLiveData(String review,int rate){
        Comment comment = getComment(review,rate);
         mCommentRepository.postCommentLiveData(comment);
    }

    private Comment getComment(String review,int rate) {

        return new Comment(mProductRepository.getUserSelectedProductId(), review,
                mCustomerDBRepository.getCustomer().getEmail(), rate);
    }
    public String getReview(Comment comment) {
        if (comment.getReview()== null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(comment.getReview(),
                    Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(comment.getReview()).toString();
        }

    }

}
