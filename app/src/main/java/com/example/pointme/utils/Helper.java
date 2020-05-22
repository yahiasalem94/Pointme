package com.example.pointme.utils;

import android.util.Range;


import org.jetbrains.annotations.Nullable;
import org.threeten.bp.DayOfWeek;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
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

    public static CalendarDay stringToDate(String stringDate) {
        if (stringDate == null) {
            return null;
        } else {
            int year = Integer.parseInt(stringDate.substring(0, 4));
            int month = Integer.parseInt(stringDate.substring(4, 6));
            int day = Integer.parseInt(stringDate.substring(6));

            CalendarDay date;
            date = CalendarDay.from(year, month, day);
            return date;
        }


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

    public static List<TreeMap<String, String>> dateBasedWorkshops( ArrayList<Map<String, String>> hashMaps, String filter ) {

        List<TreeMap<String, String>> times = convertToTreeMap(hashMaps);

        List<TreeMap<String, String>> filtered = times.stream()
                .filter(map -> map.keySet().stream()
                        .anyMatch(e -> e.contains(filter)))
                .sorted(new Comparator<TreeMap<String, String>>() {
                    @Override
                    public int compare(TreeMap<String, String> o1, TreeMap<String, String> o2) {
                        return o1.firstKey().compareTo(o2.firstKey());
                    }
                })
                .collect(Collectors.toList());

        return filtered;
    }


    @Nullable
    public static  List<TreeMap<String, String>> weeklyBasedWorkshops(ArrayList<Map<String, String>> hashMaps, CalendarDay startCalendarDay, CalendarDay endCalendarDay) {

        List<TreeMap<String, String>> times = convertToTreeMap(hashMaps);

        ArrayList<TreeMap<String, String>> out = new ArrayList<>();
        List<List<TreeMap<String, String>>> coursesPerDayPerCourse = new ArrayList<>();
        int index = 0;

        for (TreeMap<String, String> map : times) {
            out.add(new TreeMap<>());
            for (CalendarDay i = startCalendarDay; i.isBefore(endCalendarDay); i = incrementDate(i)) {
                String currentDay = Helper.getWeekDay(i);
                if (currentDay == null) return null;
                CalendarDay finalI = i;
                int finalIndex = index;
                map.keySet()
                        .stream()
                        .filter(courseDay -> courseDay.contains(currentDay))
                        .forEach(courseDay -> out.get(finalIndex).put(Helper.dateToString(finalI.getYear(), finalI.getMonth(), finalI.getDay()), courseDay));
            }

            // removing invalid starting indices
            Iterator<Map.Entry<String, String>> iterator = out.get(index).entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (!entry.getValue().contains("1")) {
                    iterator.remove();
                } else {
                    break;
                }
            }
            int sizeOut = out.get(index).size();
            int sizeTime = times.get(index).size();
            int modulus = sizeOut % sizeTime;
            while (modulus > 0) {
                out.get(index).pollLastEntry();
                modulus--;
            }


            List<TreeMap<String, String>> courseDaysByWeek = new ArrayList<>();
            int j = 0;
            for (Map.Entry<String, String> entry : out.get(index).entrySet()) {
                int internalIndex = j / map.size();
                if (courseDaysByWeek.size() == internalIndex)
                    courseDaysByWeek.add(new TreeMap<>());
                courseDaysByWeek.get(internalIndex).put(entry.getKey(), entry.getValue());
                j++;
            }

            coursesPerDayPerCourse.add(courseDaysByWeek);

            System.out.println("map per course for " + index + " course");
            System.out.println(courseDaysByWeek);

            index++;
        }

        List<TreeMap<String, String>> viewList = coursesPerDayPerCourse.stream()
                .flatMap(Collection::stream)
                .sorted(new Comparator<TreeMap<String, String>>() {
                    @Override
                    public int compare(TreeMap<String, String> o1, TreeMap<String, String> o2) {
                        return o1.firstKey().compareTo(o2.firstKey());
                    }
                })
                .collect(Collectors.toList());

        return viewList;
    }

    private static CalendarDay incrementDate(CalendarDay date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);

        c.add(Calendar.DATE, 1);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);

        return CalendarDay.from(year, month, day);
    }

    private static List<TreeMap<String,String>> convertToTreeMap(List<Map<String, String>> list)
    {
        List<TreeMap<String,String>> treeMapList = new ArrayList<>();
        for (int i = 0; i< list.size(); i++) {
            TreeMap<String, String> treeMap = new TreeMap<>();
            // Pass the hashMap to putAll() method
            treeMap.putAll(list.get(i));
            treeMapList.add(treeMap);
        }
        return treeMapList;
    }
}
