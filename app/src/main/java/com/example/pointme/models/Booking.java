package com.example.pointme.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Booking {

    private String loc;
    private boolean appr;
    private boolean canc;
    private ArrayList<String> dates;

    public Booking(){}

    public Booking(String Location, boolean Approved, boolean Cancelled, ArrayList<String> dates){
        this.loc = Location;
        this.appr = Approved;
        this.canc = Cancelled;
        this.dates = new ArrayList<>(dates);
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public boolean isAppr() {
        return appr;
    }

    public void setAppr(boolean appr) {
        this.appr = appr;
    }

    public boolean isCanc() {
        return canc;
    }

    public void setCanc(boolean canc) {
        this.canc = canc;
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
