package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

public class CourseEntry extends Entry {

    @SerializedName("courseId")
    private String courseId;

    public CourseEntry() {
    }

    public String getCourseId() {
        return courseId;
    }
}
