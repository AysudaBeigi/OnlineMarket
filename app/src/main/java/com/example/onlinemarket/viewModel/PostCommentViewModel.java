package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.data.model.customer.Customer;
import com.example.onlinemarket.data.model.product.Product;
import com.example.onlinemarket.data.repository.CommentRepository;
import com.example.onlinemarket.data.repository.CustomerDBRepository;

public class PostCommentViewModel extends AndroidViewModel {
    private CustomerDBRepository mCustomerDBRepository;
    private OnlineMarketPreferences mOnlineMarketPreferences;
    private CommentRepository mCommentRepository;
    private Product mProduct;
    private Customer mCustomer;

    public PostCommentViewModel(@NonNull Application application) {
        super(application);
        mCustomerDBRepository=CustomerDBRepository.getInstance(application);
        mOnlineMarketPreferences=OnlineMarketPreferences.getInstance(application);
        mCommentRepository= CommentRepository.getInstance(application);
        mProduct= mOnlineMarketPreferences.getUserSelectedProduct();
        mCustomer=mCustomerDBRepository.getCustomer();

    }

    public Product getProduct() {
        return mProduct;
    }
    public Customer getCustomer(){
        return mCustomer;
    }
    public MutableLiveData<Comment> getPostCommentLiveData(){
       return   mCommentRepository.getPostCommentLiveData();
    }
     public void postCommentLiveData(String review,int rate){
        Comment comment = getComment(review,rate);
         mCommentRepository.postCommentLiveData(comment);
    }
    public void setProduct(Product product){
        mOnlineMarketPreferences.setUserSelectedProduct(product);
    }
    private Comment getComment(String review,int rate) {

        return new Comment(mProduct.getId(), review,
                mCustomer.getEmail(), rate);
    }


}
