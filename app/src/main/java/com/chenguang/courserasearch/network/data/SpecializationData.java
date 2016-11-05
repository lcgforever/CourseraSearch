package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpecializationData extends EntryData {

    @SerializedName("description")
    private String description;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("logo")
    private String logo;

    @SerializedName("courseIds")
    private List<String> courseIdList;

    public SpecializationData() {
    }

    public String getDescription() {
        return description;
    }

    public String getTagline() {
        return tagline;
    }

    public String getLogo() {
        return logo;
    }

    public List<String> getCourseIdList() {
        if (courseIdList == null) {
            return new ArrayList<>();
        }
        return courseIdList;
    }
}
