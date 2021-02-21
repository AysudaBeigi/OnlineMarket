package com.example.onlinemarket.data.model.product;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    @SerializedName("image")
    private Image mImages;

    @SerializedName("count")
    private Integer mCount;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public Image getImages() {
        return mImages;
    }

    public Integer getCount() {
        return mCount;
    }

}
