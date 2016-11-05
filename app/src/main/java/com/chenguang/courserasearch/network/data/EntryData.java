package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EntryData {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("slug")
    private String slug;

    @SerializedName("partnerIds")
    private List<String> partnerIdList;

    public EntryData() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSlug() {
        return slug;
    }

    public List<String> getPartnerIdList() {
        if (partnerIdList == null) {
            return new ArrayList<>();
        }
        return partnerIdList;
    }
}
