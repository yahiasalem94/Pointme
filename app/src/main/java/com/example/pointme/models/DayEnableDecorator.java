package com.example.pointme.models;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;

public class DayEnableDecorator implements DayViewDecorator {
    private ArrayList<String> dates;
    private int mode;
    private int minPeriod;

    public DayEnableDecorator(ArrayList<String> dates, int minPeriod) {
        this.dates = new ArrayList<>(dates);
        this.minPeriod = minPeriod;
        char c = this.dates.get(0).charAt(0);
        if (c >= 'F' && c <= 'W'){
            mode = 1;
        }else{
            mode = 2;
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        CalendarDay minDate = CalendarDay.from(CalendarDay.today().getYear(), CalendarDay.today().getMonth(), CalendarDay.today().getDay() + minPeriod);
        if(day.isBefore(minDate)){
            return false;
        }
        String aday = "";
        if(mode == 1) {
            DayOfWeek dayOfWeek = day.getDate().getDayOfWeek();
            switch (dayOfWeek) {
                case FRIDAY:
                    aday = "Fr";
                    break;
                case SATURDAY:
                    aday = "Sa";
                    break;
                case SUNDAY:
                    aday = "Su";
                    break;
                case MONDAY:
                    aday = "Mo";
                    break;
                case TUESDAY:
                    aday = "Tu";
                    break;
                case WEDNESDAY:
                    aday = "We";
                    break;
                case THURSDAY:
                    aday = "Th";
                    break;
                default:
                    break;
            }
            for (String s : dates) {
                if (s.contains(aday)) {
                    return true;
                }
            }
        }else {
            PointmeDate pointmeDate = new PointmeDate(day.getDay(), day.getMonth(), day.getYear(), 0, 0);
            aday = pointmeDate.toString().substring(0,8);
            for (String s : dates) {
                if (s.contains(aday)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(false);
    }
}
