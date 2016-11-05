package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Element {

    @SerializedName("entries")
    private List<Entry> entryList;

    public Element() {
    }

    public List<Entry> getEntryList() {
        if (entryList == null) {
            return new ArrayList<>();
        }
        return entryList;
    }
}
