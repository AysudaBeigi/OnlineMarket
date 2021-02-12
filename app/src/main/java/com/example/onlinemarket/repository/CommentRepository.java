package com.example.onlinemarket.repository;

import android.content.Context;
import android.util.Log;

import com.example.onlinemarket.model.Comment;
import com.example.onlinemarket.network.NetworkParams;
import com.example.onlinemarket.network.WooCommerceAPIService;
import com.example.onlinemarket.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentRepository {
    public static String TAG = "OnlineMarket";
    private WooCommerceAPIService mWooCommerceAPIService;
    private static CommentRepository sInstance;

    public static CommentRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new CommentRepository(context);
        return sInstance;
    }

    private CommentRepository(Context context) {
        mWooCommerceAPIService = RetrofitInstance.getInstance(context).getRetrofit().
                create(WooCommerceAPIService.class);

    }

    public void postCommentAsync(Comment comment,CommentCallback commentCallback) {
        Call<Comment> call =
                mWooCommerceAPIService.postComment(comment.getProductId(),
                        comment.getReview(),
                         comment.getReviewerEmail(),
                         comment.getRating(),
                        NetworkParams.getBaseQuery());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();
                commentCallback.onItemResponse(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public interface CommentCallback {
        void onItemResponse(Comment comment);

    }

}
