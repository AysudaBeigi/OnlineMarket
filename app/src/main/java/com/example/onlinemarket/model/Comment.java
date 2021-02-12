package com.example.onlinemarket.model;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    private int mId;
    @SerializedName("product_id")
    private int mProductId;
    @SerializedName("date_created_gmt")
    private String mDateCreatedGmt;
    @SerializedName("reviewer")
    private String mReviewer;
    @SerializedName("reviewer_email")
    private String mReviewerEmail;
    @SerializedName("review")
    private String  mReview;
    @SerializedName("rating")
    private int mRating;
    @SerializedName("verified")
    private boolean mVerified;

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDateCreatedGmt() {
        return mDateCreatedGmt;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        mDateCreatedGmt = dateCreatedGmt;
    }

    public String getReviewer() {
        return mReviewer;
    }

    public void setReviewer(String reviewer) {
        mReviewer = reviewer;
    }

    public String getReviewerEmail() {
        return mReviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        mReviewerEmail = reviewerEmail;
    }

    public String getReview() {
        return mReview;
    }

    public void setReview(String review) {
        mReview = review;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public boolean isVerified() {
        return mVerified;
    }

    public void setVerified(boolean verified) {
        mVerified = verified;
    }

    public Comment(int productId,
                   String review,
                   String reviewerEmail,
                   int rating) {
        mProductId = productId;
        mReviewerEmail = reviewerEmail;
        mReview = review;
        mRating = rating;
    }
}
