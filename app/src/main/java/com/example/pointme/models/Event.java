package com.example.pointme.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Event extends Meeting {

    private String scheduleType;
    private ArrayList<String> dates;
    private HashMap<String, String> times;


    public Event() {
        super();
    }

    public Event(String name, String desc, int fees, int minPeriod, String spID, String spName, String meetingID, boolean reqAccept, String startDate,
                       String endDate, HashMap<String, String> times){

        super(name, desc, fees, minPeriod, spID, spName, meetingID, reqAccept, startDate, endDate);

        this.scheduleType = scheduleType;
        this.dates = dates;
        this.times = times;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public HashMap<String,String> getTimes() {
        return times;
    }

    public void setTimes(HashMap<String, String> times) {
        this.times = times;
    }

}
