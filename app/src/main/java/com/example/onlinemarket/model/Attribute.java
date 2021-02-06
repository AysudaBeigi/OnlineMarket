package com.example.onlinemarket.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attribute {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    @SerializedName("type")
    private String type;

    @SerializedName("order_by")
    private String orderBy;

    @SerializedName("position")
    private int position;
    @SerializedName("options")
    private List<String> options;

    public int getPosition() {

        return position;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getType() {
        return type;
    }

    public String getOrderBy() {
        return orderBy;
    }


}
