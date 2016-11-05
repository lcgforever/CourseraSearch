package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpecializationQueryResult {

    @SerializedName("elements")
    private List<SpecializationData> specializationDataList;

    public SpecializationQueryResult() {
    }

    public List<SpecializationData> getSpecializationDataList() {
        if (specializationDataList == null) {
            return new ArrayList<>();
        }
        return specializationDataList;
    }
}
