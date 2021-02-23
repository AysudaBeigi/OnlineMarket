package com.example.onlinemarket.viewModel;

import android.app.Application;

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


}
