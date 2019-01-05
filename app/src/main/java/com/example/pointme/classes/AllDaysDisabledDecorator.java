package com.example.pointme.classes;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class AllDaysDisabledDecorator implements DayViewDecorator {

    private int month;

    public AllDaysDisabledDecorator(int month){
        this.month = month;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.getMonth() == month; //decorate all days in calendar
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true); //disable all days
    }
}
