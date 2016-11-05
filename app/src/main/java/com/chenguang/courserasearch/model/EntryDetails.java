package com.chenguang.courserasearch.model;

import android.support.annotation.Nullable;

import com.chenguang.courserasearch.network.data.EntryData;
import com.chenguang.courserasearch.network.data.PartnerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntryDetails implements Comparable<EntryDetails>, Serializable {

    private String id;
    private String name;
    private String description;
    private String slug;
    private double score;
    private List<PartnerData> partnerDataList;

    public EntryDetails() {
    }

    public EntryDetails(EntryData entryData) {
        this.id = entryData.getId();
        this.name = entryData.getName();
        this.description = entryData.getDescription();
        this.slug = entryData.getSlug();
        this.partnerDataList = new ArrayList<>();
    }

    @Override
    public int compareTo(@Nullable EntryDetails another) {
        if (another == null) {
            return -1;
        }
        if (score > another.score) {
            return -1;
        } else if (score < another.score) {
            return 1;
        } else {
            return name.compareTo(another.name);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<PartnerData> getPartnerDataList() {
        return partnerDataList;
    }

    public void setPartnerDataList(List<PartnerData> partnerDataList) {
        this.partnerDataList = partnerDataList;
    }
}
