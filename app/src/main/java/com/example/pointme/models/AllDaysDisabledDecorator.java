package com.example.pointme.models;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class AllDaysDisabledDecorator implements DayViewDecorator {

    public AllDaysDisabledDecorator(){}

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true; //decorate all days in calendar
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true); //disable all days
    }
}
