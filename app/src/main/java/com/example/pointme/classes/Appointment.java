package com.example.pointme.classes;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Appointment {

    private String name;
    private String desc;
    private String loc;
    private int maxN;
    private int curN;
    private int fees;
    private double dur;
    private double timD;
    private boolean notB;
    private boolean reqA;
    private boolean canc;
    private boolean allO;
    private ArrayList<String> dates;

    public Appointment()
    {}

    public Appointment(String Name, String Description, String Location, int MaxNumber, int CurrentNumber, int Fees, double Duration, double TimeDifference, boolean NotifyBooking, boolean RequireAcceptance, boolean Cancelled, boolean allowOverlap, ArrayList<String> dates){
        this.name = Name;
        this.desc = Description;
        this.loc = Location;
        this.maxN = MaxNumber;
        this.curN = CurrentNumber;
        this.fees = Fees;
        this.timD = TimeDifference;
        this.dur = Duration;
        this.notB = NotifyBooking;
        this.reqA = RequireAcceptance;
        this.canc = Cancelled;
        this.allO = allowOverlap;
        this.dates = new ArrayList<>(dates);
    }

    public int getMaxN() {
        return maxN;
    }

    public void setMaxN(int maxN) {
        this.maxN = maxN;
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

    public int getCurN() {
        return curN;
    }

    public void setCurN(int curN) {
        this.curN = curN;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public double getDur() {
        return dur;
    }

    public void setDur(double dur) {
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

    public double getTimD() {
        return timD;
    }

    public void setTimD(double timD) {
        this.timD = timD;
    }

    public boolean isCanc() {
        return canc;
    }

    public void setCanc(boolean canc) {
        this.canc = canc;
    }

    public boolean isAllO() {
        return allO;
    }

    public void setAllO(boolean allO) {
        this.allO = allO;
    }

    @Exclude
    public ArrayList<String> getDates() {
        return dates;
    }

    @Exclude
    public void setDates(ArrayList<String> dates) {
        this.dates = new ArrayList<>(dates);
    }

}
