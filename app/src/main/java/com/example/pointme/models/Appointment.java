package com.example.pointme.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Appointment {

    private String name;
    private String desc;
    private String loc;
    private String dur;
    private String timD;
    private int fees;
    private int minP;
    private boolean notB;
    private boolean reqA;
    private boolean canc;

    public Appointment()
    {}

    public Appointment(String Name, String Description, String Location, int Fees, int minPeriod, String Duration, String TimeDifference, boolean NotifyBooking,
                       boolean RequireAcceptance, boolean Cancelled){
        this.name = Name;
        this.desc = Description;
        this.loc = Location;
        this.fees = Fees;
        this.minP = minPeriod;
        this.timD = TimeDifference;
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

    public String getTimD() {
        return timD;
    }

    public void setTimD(String timD) {
        this.timD = timD;
    }

    public boolean isCanc() {
        return canc;
    }

    public void setCanc(boolean canc) {
        this.canc = canc;
    }
}
