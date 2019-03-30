package com.example.pointme.models;

import com.example.pointme.backendCommunications.DBCom;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;

public class Booking {

    private String loc;
    private String crId;
    private String spId;
    private String type;
    private String evId;
    private @Constants.ApprovalStatus int appr;
    private boolean canc;
    private String date;
    private String spId_date;

    public Booking(){}

    public Booking(String Location, String CreatorID, String SpID, String Type, String EventID, @Constants.ApprovalStatus int Approved, boolean Cancelled, String date){
        this.loc = Location;
        this.crId = CreatorID;
        this.spId = SpID;
        this.type = Type;
        this.evId = EventID;
        this.appr = Approved;
        this.canc = Cancelled;
        this.date = date;
        this.spId_date = this.spId + "_" + this.date;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvId() {
        return evId;
    }

    public void setEvId(String evId) {
        this.evId = evId;
    }

    public @Constants.ApprovalStatus int getAppr() {
        return appr;
    }

    public void setAppr(@Constants.ApprovalStatus int appr) {
        this.appr = appr;
    }

    public boolean isCanc() {
        return canc;
    }

    public void setCanc(boolean canc) {
        this.canc = canc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSpId_date() {
        return spId_date;
    }

    public void setSpId_date(String spId_date) {
        this.spId_date = spId_date;
    }
}