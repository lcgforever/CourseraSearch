package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

public class Entry {

    @SerializedName("id")
    private String id;

    @SerializedName("resourceName")
    private String resourceName;

    @SerializedName("score")
    private double score;

    public Entry() {
    }

    public String getId() {
        return id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public double getScore() {
        return score;
    }
}
