package com.example.pointme.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointme.R;
import com.example.pointme.adapters.WorkshopAdapter;
import com.example.pointme.models.Meeting;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.models.WorkshopModel;
import com.example.pointme.utils.Helper;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.example.pointme.activities.MainActivity.MEETING;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.example.pointme.constants.Constants.WEEKLY_BASED;


public class WorkshopCalendarFragment extends Fragment implements WorkshopAdapter.WorkshopAdapterOnClickHandler, View.OnClickListener {

    private static final String TAG = WorkshopCalendarFragment.class.getSimpleName();
    private ServiceProvider profileInfo;
    private Meeting meeting;

    private String meetingId, meetingName, spId, stringStartDate, stringEndDate, scheduleType;
    private int minPeriod;
    private ArrayList<Map<String, String>> times;
    private ArrayList<String> dates;
    private ArrayList<CalendarDay> calendarDates ;

    private Date currentDate;
    private Calendar calendar;
    private int year;
    private int month;
    private String monthString;
    private StringBuilder dateBuilder;
    private String filter;
    private int monthOffset = 1;

    CalendarDay current;

    CalendarDay startDate;
    CalendarDay endDate;

    List<TreeMap<String, String>> listView;

    /*Views*/
    private RecyclerView recyclerView;
    private TextView date;
    private ImageView leftArrow;
    private ImageView rightArrow;
    private WorkshopAdapter mWorkshopAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = getArguments().getParcelable(PROFILE_INFO);
            meeting = getArguments().getParcelable(MEETING);

            meetingId = meeting.getMeetingID();
            meetingName = meeting.getName();
            spId = meeting.getSpID();
            minPeriod = meeting.getMinPeriod();
            stringStartDate = meeting.getStartDate();
            stringEndDate = meeting.getEndDate();
            scheduleType = ((WorkshopModel) meeting).getScheduleType();
            times = ((WorkshopModel) meeting).getTimes();
            dates = ((WorkshopModel) meeting).getDates();
        }

        if (stringStartDate == null) {
            startDate = CalendarDay.from(CalendarDay.today().getYear(), CalendarDay.today().getMonth(), CalendarDay.today().getDay() + minPeriod);
        } else {
            startDate = Helper.stringToDate(stringStartDate);
        }

        if (stringEndDate == null) {
            endDate = CalendarDay.from(CalendarDay.today().getYear()+1, CalendarDay.today().getMonth(), CalendarDay.today().getDay() + minPeriod);
        } else {
            endDate = Helper.stringToDate(stringEndDate);
        }

        currentDate = new Date();
        dateBuilder = new StringBuilder();
        calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        monthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        dateBuilder.append(monthString).append(" ").append(year);
        // TODO needs to change
        filter = year + "0" + (month+monthOffset);

        mWorkshopAdapter = new WorkshopAdapter(getActivity(), this);
        getActivity().setTitle("Book a Workshop");

        if (scheduleType.equals(WEEKLY_BASED)) {
            listView = Helper.weeklyBasedWorkshops(times, startDate, endDate);
        } else {
            listView = Helper.dateBasedWorkshops(times, filter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_workshop_calendar, container, false);
        date = mRootView.findViewById(R.id.date);
        leftArrow = mRootView.findViewById(R.id.left_arrow);
        rightArrow = mRootView.findViewById(R.id.right_arrow);
        recyclerView = mRootView.findViewById(R.id.workshop_recycler_view);

        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);

        date.setText(dateBuilder);
        setupRecyclerView();

        return mRootView;
    }

    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Set data adapter.
        recyclerView.setAdapter(mWorkshopAdapter);
        mWorkshopAdapter.setWorkshopData(listView);
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_arrow) {

            dateBuilder = new StringBuilder();
            calendar.add(Calendar.MONTH , -1);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            monthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            dateBuilder.append(monthString).append(" ").append(year);
            date.setText(dateBuilder);
            filter = year + "0" + (month+monthOffset);

            if (scheduleType.equals(WEEKLY_BASED)) {
                listView = Helper.weeklyBasedWorkshops(times, startDate, endDate);
            } else {
                listView = Helper.dateBasedWorkshops(times, filter);
            }

            mWorkshopAdapter.setWorkshopData(listView);
        } else if(v.getId() == R.id.right_arrow) {
            dateBuilder = new StringBuilder();

            calendar.add(Calendar.MONTH , 1);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            monthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            dateBuilder.append(monthString).append(" ").append(year);
            date.setText(dateBuilder);

            filter = year + "0" + (month+monthOffset);

            if (scheduleType.equals(WEEKLY_BASED)) {
                listView = Helper.weeklyBasedWorkshops(times, startDate, endDate);
            } else {
                listView = Helper.dateBasedWorkshops(times, filter);
            }

            mWorkshopAdapter.setWorkshopData(listView);
        }
    }
}
