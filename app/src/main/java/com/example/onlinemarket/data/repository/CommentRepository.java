package com.example.onlinemarket.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.data.model.Comment;
import com.example.onlinemarket.data.remote.NetworkParams;
import com.example.onlinemarket.data.remote.retrofit.RetrofitInstance;
import com.example.onlinemarket.data.remote.retrofit.WooCommerceAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentRepository {
    public static String TAG = "OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;
    private static CommentRepository sInstance;
    private MutableLiveData mProductCommentsLiveData;
    private MutableLiveData mPostCommentLiveData;


    public static CommentRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CommentRepository(context);
        return sInstance;
    }

    private CommentRepository(Context context) {
        mProductCommentsLiveData =new MutableLiveData();
        mPostCommentLiveData =new MutableLiveData();
        mWooCommerceAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(WooCommerceAPIService.class);


    }

    public MutableLiveData<List<Comment>> getProductCommentsLiveData() {
        return mProductCommentsLiveData;
    }

    public MutableLiveData<Comment> getPostCommentLiveData() {
        return mPostCommentLiveData;
    }

    public void postComment(Comment comment) {
        Call<Comment> call =
                mWooCommerceAPIService.postComment(comment.getProductId(),
                        comment.getReview(),
                        comment.getReviewerEmail(),
                        comment.getRating(),
                        NetworkParams.getBaseQuery());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment comment = response.body();
                mPostCommentLiveData.setValue(comment);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchComments() {
        Call<List<Comment>> call =
                mWooCommerceAPIService.getComments(NetworkParams.getBaseQuery());
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

            }
        });
    }

    public void fetchProductComments(int productId ) {
        Call<List<Comment>> call =
                mWooCommerceAPIService.getProductComments
                        (NetworkParams.getProductComments(productId));
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();
                mProductCommentsLiveData.setValue(comments);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

            }
        });
    }

}
