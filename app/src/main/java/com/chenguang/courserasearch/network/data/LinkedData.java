package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LinkedData {

    @SerializedName("partners.v1")
    private List<PartnerData> partnerDataList;

    @SerializedName("courses.v1")
    private List<CourseData> courseDataList;

    @SerializedName("onDemandSpecializations.v1")
    private List<SpecializationData> specializationDataList;

    public LinkedData() {
    }

    public List<PartnerData> getPartnerDataList() {
        if (partnerDataList == null) {
            return new ArrayList<>();
        }
        return partnerDataList;
    }

    public List<CourseData> getCourseDataList() {
        if (courseDataList == null) {
            return new ArrayList<>();
        }
        return courseDataList;
    }

    public List<SpecializationData> getSpecializationDataList() {
        if (specializationDataList == null) {
            return new ArrayList<>();
        }
        return specializationDataList;
    }
}
