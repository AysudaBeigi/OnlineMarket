package com.example.onlinemarket.repository;

import android.content.Context;

import com.example.onlinemarket.network.WooCommerceAPIService;
import com.example.onlinemarket.retrofit.RetrofitInstance;


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

    /*public void postCommentAsync(Comment comment) {
        Call<Comment> call =
                mWooCommerceAPIService.postComment(comment.getProductId(),
                        comment.getReview(),
                         comment.getRating(),
                        NetworkParams.getBaseQuery());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();


            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public interface CommentCallback {
        void onItemResponse(Comment comment);
    }*/


}
