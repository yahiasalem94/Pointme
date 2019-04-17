package com.example.pointme.decorator;

import android.graphics.drawable.Drawable;

import com.example.pointme.R;
import com.example.pointme.utils.Helper;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.HashMap;

public class DayEnableDecorator implements DayViewDecorator {
    private HashMap<Integer, ArrayList<String>> scheduleDB;
    private HashMap<String, ArrayList<String>> scheduleWB;
    private int minPeriod;
    private Drawable drawable;

    public DayEnableDecorator(HashMap<Integer, ArrayList<String>> scheduleDB, HashMap<String, ArrayList<String>> scheduleWB, int minPeriod, Drawable drawable) {
        this.scheduleDB = scheduleDB;
        this.scheduleWB = scheduleWB;
        this.minPeriod = minPeriod;
        this.drawable = drawable;
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
        view.setBackgroundDrawable(drawable);
    }
}
