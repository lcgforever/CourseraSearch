package com.chenguang.courserasearch.network.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QueryResult {

    @SerializedName("elements")
    private List<Element> elementList;

    @SerializedName("linked")
    private LinkedData linkedData;

    public QueryResult() {
    }

    public List<Element> getElementList() {
        if (elementList == null) {
            return new ArrayList<>();
        }
        return elementList;
    }

    public LinkedData getLinkedData() {
        return linkedData;
    }
}
