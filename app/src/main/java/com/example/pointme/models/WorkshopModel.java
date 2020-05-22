package com.example.pointme.models;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkshopModel extends Meeting {

    private ArrayList<String> dates;
    private String scheduleType;
    private ArrayList<Map<String, String>> times;

    public WorkshopModel() {
        super();
    }

    public WorkshopModel(String name, String desc, int fees, int minPeriod, String spID, String spName, String meetingID, boolean reqAccept,
                         String startDate, String endDate, ArrayList<Map<String, String>> times, ArrayList<String> dates, String scheduleType) {

        super(name, desc, fees, minPeriod, spID, spName, meetingID, reqAccept, startDate, endDate);
        this.dates = dates;
        this.scheduleType = scheduleType;
        this.times = times;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public ArrayList<Map<String, String>> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Map<String, String>> times) {
        this.times = times;
    }
}
