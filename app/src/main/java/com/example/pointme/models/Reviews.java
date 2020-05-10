package com.example.pointme.models;

import com.google.firebase.Timestamp;

public class Reviews {

    private int attitudeRating;
    private int avgRating;
    private String crID;
    private String crName;
    private Timestamp created;
    private String meetingID;
    private String meetingName;
    private int puncRating;
    private int qualityRating;
    private String reviewText;
    private String spID;
    private String spName;

    public Reviews() {

    }

    public Reviews(int attitudeRating, int avgRating, String crID, String crName, Timestamp created, String meetingID, String meetingName, int puncRating, int qualityRating,
                   String reviewText, String spID, String spName) {
        this.attitudeRating = attitudeRating;
        this.avgRating = avgRating;
        this.crID = crID;
        this.crName = crName;
        this.created = created;
        this.meetingID = meetingID;
        this.meetingName = meetingName;
        this.puncRating = puncRating;
        this.qualityRating = qualityRating;
        this.reviewText = reviewText;
        this.spID = spID;
        this.spName = spName;
    }

    public int getAttitudeRating() {
        return attitudeRating;
    }

    public void setAttitudeRating(int attitudeRating) {
        this.attitudeRating = attitudeRating;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public String getCrID() {
        return crID;
    }

    public void setCrID(String crID) {
        this.crID = crID;
    }

    public String getCrName() {
        return crName;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public int getPuncRating() {
        return puncRating;
    }

    public void setPuncRating(int puncRating) {
        this.puncRating = puncRating;
    }

    public int getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(int qualityRating) {
        this.qualityRating = qualityRating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
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
}
