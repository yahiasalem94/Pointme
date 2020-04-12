package com.example.pointme.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Event extends Meeting {

    private String scheduleType;
    private ArrayList<String> dates;



    public Event() {
        super();
    }

    public Event(String name, String desc, int fees, int minPeriod, String spID, String spName, String meetingID, boolean reqAccept, String startDate,
                       String endDate, HashMap<String, String> times, String timeDiff, String duration){

        super(name, desc, fees, minPeriod, spID, spName, meetingID, reqAccept, startDate, endDate, times);

        this.scheduleType = scheduleType;
        this.dates = dates;
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

}
