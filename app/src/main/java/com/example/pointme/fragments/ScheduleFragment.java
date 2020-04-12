package com.example.pointme.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pointme.R;
import com.example.pointme.adapters.ScheduleListAdapter;
import com.example.pointme.models.ScheduleList;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private MaterialCalendarView mMaterialCalendarView;
    private ScheduleListAdapter adapter;

    RecyclerView list;
    LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ScheduleListAdapter(createList());
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

    /*    list = view.findViewById(R.id.card_view_list);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        // Set data adapter.
        list.setAdapter(adapter);*/

    }

    private List<ScheduleList> createList() {

        List<ScheduleList> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<String>();

        for (int i = 0; i < 5; i++)
        {
            ScheduleList list = new ScheduleList();
            list.setTitle("yahia" + " " + i);
            result.add(list);
        }

        return result;
    }


}