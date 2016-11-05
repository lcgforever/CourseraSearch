package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

public class CourseData extends EntryData {

    @SerializedName("courseType")
    private String courseType;

    @SerializedName("photoUrl")
    private String photoUrl;

    public CourseData() {
    }

    public String getCourseType() {
        return courseType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
