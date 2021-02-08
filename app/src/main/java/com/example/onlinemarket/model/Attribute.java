package com.example.onlinemarket.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attribute {@SerializedName("visible")
private boolean visible;

    @SerializedName("name")
    private String name;

    @SerializedName("options")
    private List<String> options;

    @SerializedName("id")
    private int id;

    @SerializedName("position")
    private int position;

    @SerializedName("variation")
    private boolean variation;

    public String getName() {
        return name;
    }

    public List<String> getOptions() {
        return options;
    }
}