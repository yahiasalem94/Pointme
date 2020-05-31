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

//    public static HashMap<String, Boolean> convertDateListToMap(ArrayList<String> dates) {
//        HashMap<String, Boolean> map = new HashMap<>();
//        for (String date : dates) {
//            map.put(date, true);
//        }
//        return map;
//    }
//
//    public static ArrayList<String> convertMapToDateList(HashMap<String, Boolean> map) {
//        return new ArrayList<>(map.keySet());
//    }

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

    public static List<TreeMap<String, String>> filterWeeklyBasedWorkshops(List<TreeMap<String, String>> maps, String filter ) {

        List<TreeMap<String, String>> filtered = maps.stream()
                .filter(map -> map.keySet().stream()
                        .anyMatch(e -> e.contains(filter)))
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
            for (CalendarDay i = startCalendarDay; i.isBefore(endCalendarDay); i = incrementDateByDays(i, 1)) {
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

        // replacing days with dates
        for (int replaceIndex = 0; replaceIndex < viewList.size(); replaceIndex++) {
            Iterator<Map.Entry<String, String>> iterator = viewList.get(replaceIndex).entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                for (TreeMap<String, String> map : times) {
                    if (map.containsKey(entry.getValue())) {
                        entry.setValue(map.get(entry.getValue()));
                    }
                }
            }
        }

        return viewList;
    }

    public static CalendarDay incrementDateByDays(CalendarDay date, int incrementBy) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);

        c.add(Calendar.DAY_OF_MONTH, incrementBy);

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
