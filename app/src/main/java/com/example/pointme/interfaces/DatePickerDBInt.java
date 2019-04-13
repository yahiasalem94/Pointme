package com.example.pointme.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

public interface DatePickerDBInt {
    void setAppBookingSlots(HashMap<Integer, ArrayList<String>> scheduleDBMap, HashMap<String, ArrayList<String>> scheduleWBMap);

    void setAppScheduleAndScheduleWB(ArrayList<String> appSchedule, ArrayList<String> scheduleWB);
}
