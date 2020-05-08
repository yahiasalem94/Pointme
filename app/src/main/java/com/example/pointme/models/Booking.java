package com.example.pointme.models;

import java.util.ArrayList;

public class Booking {

    private String spID;
    private String crID;
    private String spName;
    private String meetingName;
    private String type;
    private String approvalStatus;
    private boolean cancelled;
    private ArrayList<BookingDetails> bookingDetails;

    public Booking(){

    }

    public Booking(String spID, String CreatorID, String spName, String meetingName, String type, String approvalStatus,
                   boolean cancelled, ArrayList<BookingDetails> bookingDetails){
        this.spID = spID;
        this.crID = CreatorID;
        this.spName = spName;
        this.meetingName = meetingName;
        this.type = type;
        this.approvalStatus = approvalStatus;
        this.cancelled = cancelled;
        this.bookingDetails = bookingDetails;
    }

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
}