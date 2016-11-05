package com.chenguang.courserasearch.model;

import com.chenguang.courserasearch.network.data.SpecializationData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecializationDetails extends EntryDetails implements Serializable {

    private String tagline;
    private String logo;
    private List<CourseDetails> courseDetailsList;

    public SpecializationDetails() {
    }

    public SpecializationDetails(SpecializationData specializationData) {
        super(specializationData);
        this.tagline = specializationData.getTagline();
        this.logo = specializationData.getLogo();
        this.courseDetailsList = new ArrayList<>();
    }

    public String getTagline() {
        return tagline;
    }

    public String getLogo() {
        return logo;
    }

    public List<CourseDetails> getCourseDetailsList() {
        return courseDetailsList;
    }

    public void setCourseDetailsList(List<CourseDetails> courseDetailsList) {
        this.courseDetailsList = courseDetailsList;
    }
}
