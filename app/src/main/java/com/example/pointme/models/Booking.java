package com.example.pointme.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Booking implements Parcelable {

    private String spID;
    private String crID;
    private String spName;
    private String meetingName;
    private String type;
    private String approvalStatus;
    private String meetingID;
    private boolean cancelled;
    private ArrayList<BookingDetails> bookingDetails;

    public Booking(){

    }

    public Booking(String spID, String CreatorID, String spName, String meetingName, String type, String approvalStatus,
                   String meetingID, boolean cancelled, ArrayList<BookingDetails> bookingDetails){
        this.spID = spID;
        this.crID = CreatorID;
        this.spName = spName;
        this.meetingName = meetingName;
        this.type = type;
        this.approvalStatus = approvalStatus;
        this.meetingID = meetingID;
        this.cancelled = cancelled;
        this.bookingDetails = bookingDetails;
    }

    protected Booking(Parcel in) {
        spID = in.readString();
        crID = in.readString();
        spName = in.readString();
        meetingName = in.readString();
        type = in.readString();
        approvalStatus = in.readString();
        meetingID = in.readString();
        cancelled = in.readByte() != 0;
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public String getSpID() {
        return spID;
    }

    public void setSpID(String spID) {
        this.spID = spID;
    }

    public String getCrID() {
        return crID;
    }

    public void setCrID(String crID) {
        this.crID = crID;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public ArrayList<BookingDetails> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(ArrayList<BookingDetails> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spID);
        dest.writeString(crID);
        dest.writeString(spName);
        dest.writeString(meetingName);
        dest.writeString(type);
        dest.writeString(approvalStatus);
        dest.writeByte((byte) (cancelled ? 1 : 0));
    }
}