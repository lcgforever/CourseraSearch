package com.chenguang.courserasearch.model;

import com.chenguang.courserasearch.network.data.SpecializationData;

import java.util.ArrayList;
import java.util.List;

public class SpecializationDetails extends EntryDetails {

    private String description;
    private String tagline;
    private String logo;
    private List<CourseDetails> courseDetailsList;

    public SpecializationDetails() {
    }

    public SpecializationDetails(SpecializationData specializationData) {
        super(specializationData);
        this.description = specializationData.getDescription();
        this.tagline = specializationData.getTagline();
        this.logo = specializationData.getLogo();
        this.courseDetailsList = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<CourseDetails> getCourseDetailsList() {
        return courseDetailsList;
    }

    public void setCourseDetailsList(List<CourseDetails> courseDetailsList) {
        this.courseDetailsList = courseDetailsList;
    }
}
