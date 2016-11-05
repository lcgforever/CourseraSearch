package com.chenguang.courserasearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chenguang.courserasearch.network.data.CourseData;
import com.chenguang.courserasearch.network.data.PartnerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends EntryDetails implements Serializable, Parcelable {

    private String courseType;
    private String photoUrl;

    public static final Creator<CourseDetails> CREATOR = new Creator<CourseDetails>() {
        @Override
        public CourseDetails createFromParcel(Parcel in) {
            return new CourseDetails(in);
        }

        @Override
        public CourseDetails[] newArray(int size) {
            return new CourseDetails[size];
        }
    };

    public CourseDetails() {
    }

    public CourseDetails(CourseData courseData) {
        super(courseData);
        this.courseType = courseData.getCourseType();
        this.photoUrl = courseData.getPhotoUrl();
    }

    protected CourseDetails(Parcel in) {
        courseType = in.readString();
        photoUrl = in.readString();
        setId(in.readString());
        setName(in.readString());
        setDescription(in.readString());
        setSlug(in.readString());
        setScore(in.readDouble());
        List<PartnerData> partnerDataList = new ArrayList<>();
        in.readTypedList(partnerDataList, PartnerData.CREATOR);
        setPartnerDataList(partnerDataList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseType);
        dest.writeString(photoUrl);
        dest.writeString(getId());
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeString(getSlug());
        dest.writeDouble(getScore());
        dest.writeTypedList(getPartnerDataList());
    }

    public String getCourseType() {
        return courseType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
