package com.example.pointme.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Meeting implements Parcelable {

    private String name;
    private String desc;
    private int fees;
    private int minPeriod;
    private String spID;
    private String spName;
    private String meetingID;
    private boolean reqAccept;
    private String startDate;
    private String endDate;
//    private HashMap<String, String> times;

    public Meeting(){

    }

    public Meeting(String name, String desc, int fees, int minPeriod, String spID, String spName, String meetingID, boolean reqAccept, String startDate, String endDate) {
        this.name = name;
        this.desc = desc;
        this.fees = fees;
        this.minPeriod = minPeriod;
        this.spID = spID;
        this.spName = spName;
        this.meetingID = meetingID;
        this.reqAccept = reqAccept;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected Meeting(Parcel in) {
        name = in.readString();
        desc = in.readString();
        fees = in.readInt();
        minPeriod = in.readInt();
        spID = in.readString();
        spName = in.readString();
        meetingID = in.readString();
        reqAccept = in.readByte() != 0;
        startDate = in.readString();
        endDate = in.readString();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public int getMinPeriod() {
        return minPeriod;
    }

    public void setMinPeriod(int minPeriod) {
        this.minPeriod = minPeriod;
    }

    public String getSpID() {
        return spID;
    }

    public void setSpID(String spID) {
        this.spID = spID;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public boolean isReqAccept() {
        return reqAccept;
    }

    public void setReqAccept(boolean reqAccept) {
        this.reqAccept = reqAccept;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeInt(fees);
        dest.writeInt(minPeriod);
        dest.writeString(spID);
        dest.writeString(spName);
        dest.writeString(meetingID);
        dest.writeByte((byte) (reqAccept ? 1 : 0));
        dest.writeString(startDate);
        dest.writeString(endDate);
    }
}
