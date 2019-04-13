package com.example.pointme.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Appointment {

    private String name;
    private String desc;
    private String loc;
    private String dur;
    private String key;
    private String spId;
    private int fees;
    private int minP;
    private boolean notB;
    private boolean reqA;
    private boolean canc;

    public Appointment()
    {}

    public Appointment(String Name, String Description, String Location, String SpID, int Fees, int minPeriod, String Duration, boolean NotifyBooking,
                       boolean RequireAcceptance, boolean Cancelled){
        this.name = Name;
        this.desc = Description;
        this.loc = Location;
        this.key = null;
        this.spId = SpID;
        this.fees = Fees;
        this.minP = minPeriod;
        this.dur = Duration;
        this.notB = NotifyBooking;
        this.reqA = RequireAcceptance;
        this.canc = Cancelled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String description) {
        this.desc = description;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String location) {
        this.loc = location;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public int getFees() {
        return fees;
    }

    public int getMinP() {
        return minP;
    }

    public void setMinP(int minP) {
        this.minP = minP;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur = dur;
    }

    public boolean isNotB() {
        return notB;
    }

    public void setNotB(boolean notB) {
        this.notB = notB;
    }

    public boolean isReqA() {
        return reqA;
    }

    public void setReqA(boolean reqA) {
        this.reqA = reqA;
    }

    public boolean isCanc() {
        return canc;
    }

    public void setCanc(boolean canc) {
        this.canc = canc;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
