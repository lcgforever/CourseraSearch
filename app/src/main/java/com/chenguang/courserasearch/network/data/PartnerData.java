package com.chenguang.courserasearch.network.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PartnerData implements Serializable, Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("shortName")
    private String shortName;

    public static final Creator<PartnerData> CREATOR = new Creator<PartnerData>() {
        @Override
        public PartnerData createFromParcel(Parcel in) {
            return new PartnerData(in);
        }

        @Override
        public PartnerData[] newArray(int size) {
            return new PartnerData[size];
        }
    };

    public PartnerData() {
    }

    protected PartnerData(Parcel in) {
        id = in.readString();
        name = in.readString();
        shortName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(shortName);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
