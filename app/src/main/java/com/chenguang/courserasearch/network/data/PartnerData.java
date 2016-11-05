package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

public class PartnerData {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("shortName")
    private String shortName;

    public PartnerData() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
