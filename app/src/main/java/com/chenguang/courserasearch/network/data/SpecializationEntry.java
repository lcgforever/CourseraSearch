package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

public class SpecializationEntry extends Entry {

    @SerializedName("onDemandSpecializationId")
    private String onDemandSpecializationId;

    public SpecializationEntry() {
    }

    public String getOnDemandSpecializationId() {
        return onDemandSpecializationId;
    }
}
