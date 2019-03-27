package com.example.pointme.models;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;

public class DayEnableDecorator implements DayViewDecorator {
    private HashMap<Integer, ArrayList<String>> scheduleDB;
    private HashMap<String, ArrayList<String>> scheduleWB;
    private int minPeriod;

    public DayEnableDecorator(HashMap<Integer, ArrayList<String>> scheduleDB, HashMap<String, ArrayList<String>> scheduleWB, int minPeriod) {
        this.scheduleDB = scheduleDB;
        this.scheduleWB = scheduleWB;
        this.minPeriod = minPeriod;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        CalendarDay minDate = CalendarDay.from(CalendarDay.today().getYear(), CalendarDay.today().getMonth(), CalendarDay.today().getDay() + minPeriod);
        if(day.isBefore(minDate)){
            return false;
        }
        if(!scheduleDB.isEmpty() && scheduleDB.containsKey(day.getDay())){
            return true;
        }
        String weekDay = Helper.getWeekDay(day);
        return scheduleWB.containsKey(weekDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(false);
    }
}
