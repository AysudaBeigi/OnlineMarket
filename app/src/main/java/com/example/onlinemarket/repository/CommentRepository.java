package com.example.onlinemarket.repository;

import android.content.Context;
import android.util.Log;

import com.example.onlinemarket.model.Comment;
import com.example.onlinemarket.network.NetworkParams;
import com.example.onlinemarket.network.WooCommerceAPIService;
import com.example.onlinemarket.retrofit.RetrofitInstance;

import java.util.List;

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

    public void postComment(Comment comment, CommentCallback commentCallback) {
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

    public void fetchComments(CommentsCallback commentsCallback) {
        Call<List<Comment>> call =
                mWooCommerceAPIService.getComments(NetworkParams.getBaseQuery());
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();
                commentsCallback.onItemResponse(comments);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

            }
        });
    }

    public void fetchProductComments(int productId,CommentsCallback commentsCallback) {
        Call<List<Comment>> call =
                mWooCommerceAPIService.getProductComments
                        (NetworkParams.getProductComments(productId));
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();
                commentsCallback.onItemResponse(comments);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);

            }
        });
    }


    public interface CommentCallback {
        void onItemResponse(Comment comment);

    }

    public interface CommentsCallback {
        void onItemResponse(List<Comment> comments);

    }

}
