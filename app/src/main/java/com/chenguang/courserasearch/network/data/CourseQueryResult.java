package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CourseQueryResult {

    @SerializedName("elements")
    private List<CourseData> courseDataList;

    public CourseQueryResult() {
    }

    public List<CourseData> getCourseDataList() {
        if (courseDataList == null) {
            return new ArrayList<>();
        }
        return courseDataList;
    }
}
