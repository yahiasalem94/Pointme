package com.example.pointme.decorator;

import android.graphics.drawable.Drawable;

import com.example.pointme.utils.Helper;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DayEnableDecorator implements DayViewDecorator {
    private List<String>  days;
    private ArrayList<LocalDate>  dates;
    private HashMap<String, ArrayList<String>> scheduleWB;
    private int minPeriod;
    private String stringStartDate;
    private String stringEndDate;
    private Drawable drawable;

    public DayEnableDecorator(List<String> days/*, HashMap<String, ArrayList<String>> scheduleWB,*/, int minPeriod, String stringStartDate, String stringEndDate) {
        this.days = days;
        this.minPeriod = minPeriod;
        this.stringStartDate = stringStartDate;
        this.stringEndDate = stringEndDate;
    }

    public DayEnableDecorator(ArrayList<LocalDate>  dates/*, HashMap<String, ArrayList<String>> scheduleWB,*/, int minPeriod, String stringStartDate, String stringEndDate) {
        this.dates = dates;
        this.minPeriod = minPeriod;
        this.stringStartDate = stringStartDate;
        this.stringEndDate = stringEndDate;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        CalendarDay minDate = Helper.incrementDateByDays(CalendarDay.today(), minPeriod);
        CalendarDay startDate = Helper.stringToDate(stringStartDate);
        CalendarDay endDate = Helper.stringToDate(stringEndDate);

        if(day.isBefore(minDate)){
            return false;
        }
        if (days != null) {
            if (startDate != null && endDate != null) {
                if (day.isBefore(startDate)) {
                    return false;
                }

                if (day.isAfter(endDate)) {
                    return false;
                }
            }

            String weekDay = Helper.getWeekDay(day);
            return days.contains(weekDay);
        } else if (!dates.isEmpty()) {

            return (dates.contains(day.getDate()));
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(false);
    }
}
