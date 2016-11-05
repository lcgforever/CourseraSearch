package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

public class PagingData {

    @SerializedName("total")
    private int total;

    public PagingData() {
    }

    public int getTotal() {
        return total;
    }
}
