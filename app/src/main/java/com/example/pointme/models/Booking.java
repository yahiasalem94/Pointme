package com.example.pointme.models;

public class Booking {

    private String spID;
    private String crId;
    private String meetingID;
    private String spName;
    private String meetingName;
    private String date;
    private String time;
    private String type;
    private String approvalStatus;
    private boolean cancelled;

    public Booking(){}

    public Booking(String spID, String CreatorID, String meetingID, String spName, String meetingName,String date, String time, String approvalStatus, boolean cancelled){
        this.spID = spID;
        this.crId = CreatorID;
        this.meetingID = meetingID;
        this.spName = spName;
        this.meetingName = meetingName;
        this.date = date;
        this.time = time;
        this.type = type;
        this.approvalStatus = approvalStatus;
        this.cancelled = cancelled;
    }

    public String getSpID() {
        return spID;
    }

    public void setSpID(String spID) {
        this.spID = spID;
    }

    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
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

    public void setMeetingName(String meetingID) {
        this.meetingName = meetingName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}