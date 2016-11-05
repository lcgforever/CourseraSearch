package com.chenguang.courserasearch.model;

import com.chenguang.courserasearch.network.data.CourseData;

public class CourseDetails extends EntryDetails {

    private String courseType;
    private String photoUrl;

    public CourseDetails() {
    }

    public CourseDetails(CourseData courseData) {
        super(courseData);
        this.courseType = courseData.getCourseType();
        this.photoUrl = courseData.getPhotoUrl();
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
