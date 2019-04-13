package com.example.pointme.utils;

import android.util.Range;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper {

    public static ArrayList<Range<String>> getFreeRanges(String scheduleDB, String scheduleWB, String appSchedule){
        ArrayList<Range<String>> freeRanges = new ArrayList<>();
        ArrayList<Range<String>> freeTemp = new ArrayList<>();
        ArrayList<Range<String>> freeApp = new ArrayList<>();
        String schedule;

        if (scheduleDB == null && scheduleWB == null){
            freeRanges.add(new Range<>(appSchedule.substring(2, 6), appSchedule.substring(6, 10)));
            return freeRanges;
        }
        if (scheduleDB != null) {
            schedule = "!!!!" + scheduleDB.substring(8) + "9999";
        } else {
            schedule = "!!!!" + scheduleWB.substring(2) + "9999";
        }
        for (int i = 0; i < schedule.length(); i = i + 8){
            freeTemp.add(new Range<>(schedule.substring(i, i + 4), schedule.substring(i + 4, i + 8)));
        }
        for (int i = 2; i < appSchedule.length(); i = i + 8){
            freeApp.add(new Range<>(appSchedule.substring(i, i + 4), appSchedule.substring(i + 4, i + 8)));
        }
        for (Range<String> freeAppRange : freeApp){
            for (Range<String> freeTempRange : freeTemp) {
                if (freeAppRange.contains(freeTempRange.getLower()) || freeAppRange.contains(freeTempRange.getUpper())
                        || freeTempRange.contains(freeAppRange.getLower()) || freeTempRange.contains(freeAppRange.getUpper())) {
                    Range<String> freeRange = freeAppRange.intersect(freeTempRange);
                    if (!freeRange.getLower().equals(freeRange.getUpper())) {
                        freeRanges.add(freeRange);
                    }
                }
            }
        }
        return freeRanges;
    }

    public static ArrayList<Range<String>> getBusyRanges(String scheduleDB, String scheduleWB){
        ArrayList<Range<String>> busyRanges = new ArrayList<>();
        String schedule;

        if (scheduleDB == null && scheduleWB == null){
            return null;
        }
        if (scheduleDB != null) {
            schedule = scheduleDB.substring(8);
        } else {
            schedule = scheduleWB.substring(2);
        }
        for (int i = 0; i < schedule.length(); i = i + 8){
            busyRanges.add(new Range<>(schedule.substring(i, i + 4), schedule.substring(i + 4, i + 8)));
        }
        return busyRanges;
    }

    public static String addDuration(String time, String duration) {
        String startHour = time.substring(0, 2);
        String startMinute = time.substring(2);
        String string = "", tempS;
        int endMinute = Integer.parseInt(startMinute) + Integer.parseInt(duration.substring(2));
        int temp = 0;
        if (endMinute >= 60) {
            endMinute -= 60;
            temp = 1;
        }
        int endHour = Integer.parseInt(startHour) + temp + Integer.parseInt(duration.substring(0, 2));
        if (endHour >= 24) {
            endHour -= 24;
        }
        tempS = String.valueOf(endHour);
        if (tempS.length() == 1) {
            tempS = "0" + tempS;
        }
        string += tempS;
        tempS = String.valueOf(endMinute);
        if (tempS.length() == 1) {
            tempS = "0" + tempS;
        }
        string += tempS;
        return string;
    }

    public static ArrayList<String> getBookingSlots(String scheduleDB, String scheduleWB, String appSchedule, String duration, String timeD){
        ArrayList<Range<String>> freeRanges = getFreeRanges(scheduleDB, scheduleWB, appSchedule);
        ArrayList<Range<String>> busyRanges = getBusyRanges(scheduleDB, scheduleWB);
        ArrayList<String> bookingSlots = new ArrayList<>();

        for(Range<String> range : freeRanges){
            String lowerLimit = range.getLower();
            String lowerLim;
            if(busyRanges == null || !isTimeInRangeList(lowerLimit, busyRanges)){
                lowerLim = lowerLimit;
            }else{
                lowerLim = addDuration(lowerLimit, timeD);
            }
            boolean flag = true;
            while(flag){
                String upperLim = addDuration(lowerLim, duration);
                String upperLimit = addDuration(upperLim, timeD);
                if(range.contains(new Range<>(lowerLim, upperLimit))
                        || (range.contains(new Range<>(lowerLim, upperLim)) && (busyRanges == null || !isTimeInRangeList(upperLim, busyRanges)))){
                    bookingSlots.add(lowerLim + upperLim);
                    lowerLim = addDuration(lowerLim, "0030");
                }else{
                    flag = false;
                }
            }
        }
        return bookingSlots;
    }

    public static boolean isTimeInRangeList(String time, ArrayList<Range<String>> arrayList){
        for(Range<String> range : arrayList){
            if(range.contains(time)){
                return true;
            }
        }
        return false;
    }

    public static String getWeekDay(CalendarDay day){
        DayOfWeek weekDay = day.getDate().getDayOfWeek();
        switch (weekDay) {
            case FRIDAY:
                return "Fr";
            case SATURDAY:
                return "Sa";
            case SUNDAY:
                return "Su";
            case MONDAY:
                return "Mo";
            case TUESDAY:
                return "Tu";
            case WEDNESDAY:
                return "We";
            case THURSDAY:
                return "Th";
            default:
                return null;
        }
    }

    public static String dateToString(int year, int month, int day){
        String date = "";
        String temp;
        if(year != 0) {
            temp = String.valueOf(year);
            date += temp;
        }
        if(month !=0){
            temp = String.valueOf(month);
            if(temp.length() == 1){
                temp = "0" + temp;
            }
            date += temp;
        }
        if(day != 0) {
            temp = String.valueOf(day);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            date += temp;
        }
        return date;
    }

    public static HashMap<String, Boolean> convertDateListToMap(ArrayList<String> dates) {
        HashMap<String, Boolean> map = new HashMap<>();
        for (String date : dates) {
            map.put(date, true);
        }
        return map;
    }

    public static ArrayList<String> convertMapToDateList(HashMap<String, Boolean> map) {
        return new ArrayList<>(map.keySet());
    }
}
