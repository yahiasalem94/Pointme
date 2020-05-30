package com.example.pointme.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Appointment extends Meeting {

    private String timeDiff;
    private String duration;
    private HashMap<String, String> times;


    public Appointment()
    {
        super();
    }

    public Appointment(String name, String desc, int fees, int minPeriod, String spID, String spName, String meetingID, boolean reqAccept, String startDate,
                       String endDate, HashMap<String, String> times, String timeDiff, String duration){
        super(name, desc, fees, minPeriod, spID, spName, meetingID, reqAccept, startDate, endDate);
        this.timeDiff = timeDiff;
        this.duration = duration;
        this.times = times;
    }

    public String getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(String timeDiff) {
        this.timeDiff = timeDiff;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public HashMap<String,String> getTimes() {
        return times;
    }

    public void setTimes(HashMap<String, String> times) {
        this.times = times;
    }
}
