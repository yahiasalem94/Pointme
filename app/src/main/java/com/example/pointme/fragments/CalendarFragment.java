package com.example.pointme.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pointme.R;
import com.example.pointme.constants.Type;
import com.example.pointme.decorator.AllDaysDisabledDecorator;
import com.example.pointme.decorator.DayEnableDecorator;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.Meeting;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.Helper;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static com.example.pointme.activities.MainActivity.APPOINTMENT;
import static com.example.pointme.activities.MainActivity.EVENT;
import static com.example.pointme.activities.MainActivity.MEETING;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.example.pointme.activities.MainActivity.TYPE;

public class CalendarFragment extends Fragment {

    private ServiceProvider profileInfo;
    private Event event;
    private Appointment appointment;
    private @Type int type;


    private int minPeriod;
    private String startDate;
    private String endDate;
    private HashMap<String, String> times;
    private ArrayList<String> days;
    /* Appointment */
    private String timeDiff;
    /*  Events */
    private String schedueType;
    private ArrayList<String> stringDates;
    private ArrayList<LocalDate> dates = new ArrayList<>();
    private CalendarDay minDate;

    /* Views */
    private MaterialCalendarView mMaterialCalendarView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = getArguments().getParcelable(PROFILE_INFO);
            type = getArguments().getInt(TYPE);
            if (type == Type.EVENT) {
                event = getArguments().getParcelable(MEETING);
                minPeriod = event.getMinPeriod();
                startDate = event.getStartDate();
                endDate = event.getEndDate();
                schedueType = event.getScheduleType();
                stringDates = event.getDates();
                times = (HashMap<String, String>) event.getTimes();
            } else {
                appointment = getArguments().getParcelable(MEETING);
                minPeriod = appointment.getMinPeriod();
                startDate = appointment.getStartDate();
                endDate = appointment.getEndDate();
                schedueType = "WB";
                times = (HashMap<String, String>) appointment.getTimes();
                timeDiff = appointment.getTimeDiff();
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_date_picker, container, false);

        mMaterialCalendarView = mRootView.findViewById(R.id.calendarView);
        mMaterialCalendarView.addDecorator(new AllDaysDisabledDecorator());
        handleAppointments();
        mMaterialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay calendarDay, boolean selected) {

            }
        });

        return mRootView;
    }

    private void handleAppointments() {
        days = new ArrayList<>(times.keySet());

        if (schedueType == "WB") {
            mMaterialCalendarView.addDecorator(new DayEnableDecorator(days, minPeriod, startDate, endDate));
        } else {
            for (int i = 0; i < stringDates.size(); i++) {
                CalendarDay date = Helper.stringToDate(stringDates.get(i));
                dates.add(date.getDate());
            }
            mMaterialCalendarView.addDecorator(new DayEnableDecorator(dates, minPeriod, startDate, endDate));
        }

    }
}
