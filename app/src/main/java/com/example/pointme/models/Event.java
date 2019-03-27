package com.example.pointme.models;

import com.example.pointme.backend.DBCom;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;

public class Event implements Cloneable{

    private String name;
    private String desc;
    private String loc;
    private String key;
    private String spId;
    private int maxN;
    private int curN;
    private int fees;
    private int minP;
    private String dur;
    private String sdate;
    private String edate;
    private boolean notB;
    private boolean reqA;
    private boolean canc;
    private HashMap<String, Boolean> dates;

    public Event() {}

    public Event(String Name, String Description, String Location, String SpID, int MaxNumber, int CurrentNumber, int Fees, int minPeriod, String Duration,
                 boolean NotifyBooking, boolean RequireAcceptance, boolean Cancelled, String StartDate, String EndDate, ArrayList<String> dates){
        this.name = Name;
        this.desc = Description;
        this.loc = Location;
        this.spId = SpID;
        this.key = null;
        this.maxN = MaxNumber;
        this.curN = CurrentNumber;
        this.fees = Fees;
        this.minP = minPeriod;
        this.dur = Duration;
        this.notB = NotifyBooking;
        this.reqA = RequireAcceptance;
        this.canc = Cancelled;
        this.sdate = StartDate;
        this.edate = EndDate;
        this.dates = DBCom.convertDateListToMap(dates);
    }

    @Override
    protected Object clone(){
        try {
            return super.clone();
        }catch (CloneNotSupportedException e){
            return null;
        }
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

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
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

    public int getMinP() {
        return minP;
    }

    public void setMinP(int minP) {
        this.minP = minP;
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

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    @Exclude
    public HashMap<String, Boolean> getDates() {
        return dates;
    }

    @Exclude
    public void setDates(HashMap<String, Boolean> dates) {
        this.dates = new HashMap<>(dates);
    }

    @Exclude
    public ArrayList<String> getDatesAsList(){
        return DBCom.convertMapToDateList(dates);
    }

    @Exclude
    public void setDatesFromList(ArrayList<String> dates){
        this.dates = DBCom.convertDateListToMap(dates);
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
