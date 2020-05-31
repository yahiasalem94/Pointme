package com.example.pointme;

import com.example.pointme.utils.Helper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testIncrement() {
        System.out.println("xxx");
        long timeMillis = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis());
        ArrayList<TreeMap<String, String>> times = new ArrayList<>();


        CalendarDay date = CalendarDay.from(2020, 5, 20);

        String startDate = "20200515";
        String endDate = "20200620";

        CalendarDay startCalendarDay = Helper.stringToDate(startDate);
        CalendarDay endCalendarDay = Helper.stringToDate(endDate);

        times.add(new TreeMap<String, String>() {{
            put("1Monday", "100014000");
            put("2Tuesday", "100014000");
            put("3Thursday", "100014000");
        }});

        times.add(new TreeMap<String, String>() {{
            put("1Wednesday", "10001400");
            put("2Sunday", "100014000");
        }});


        // map times to tree map

        List<CalendarDay> output = new ArrayList<>();
        ArrayList<TreeMap<String, String>> out = new ArrayList<>();
        List<List<TreeMap<String, String>>> coursesPerDayPerCourse = new ArrayList<>();
        int index = 0;

        for (TreeMap<String, String> map : times) {
            out.add(new TreeMap<>());
            for (CalendarDay i = startCalendarDay; i.isBefore(endCalendarDay); i = incrementDate(i)) {
                String currentDay = Helper.getWeekDay(i);
                if (currentDay == null) return;
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

        long LasttimeMillis = System.currentTimeMillis();
        System.out.println(LasttimeMillis - timeMillis);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("before final Results");
        System.out.println(gson.toJson(coursesPerDayPerCourse));
        System.out.println("final stage");
        System.out.println(gson.toJson(viewList));
////        System.out.println(output);
//        System.out.println("xxx");
//        System.out.println(out);


    }

    static CalendarDay incrementDate(CalendarDay date) {
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
}