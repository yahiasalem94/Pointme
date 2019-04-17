package com.example.pointme.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class ScheduleFragment extends Fragment {

    private MaterialCalendarView mMaterialCalendarView;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        /* TODO Needs to be linked with backend to get schedule for a specific`134uio service providor */
        mMaterialCalendarView = view.findViewById(R.id.calendarView);
        mMaterialCalendarView.setAllowClickDaysOutsideCurrentMonth(false);

    }


}