package com.example.pointme.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.pointme.R;
import com.example.pointme.activities.ServiceProviderMainActivity;
import com.example.pointme.decorator.AllDaysDisabledDecorator;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


public class ProviderCalendarView extends Fragment {

    private static final String TAG = ProviderCalendarView.class.getSimpleName();

    private MaterialCalendarView mMaterialCalendarView;
    private LinearLayout layoutBottomSheet;

    BottomSheetBehavior sheetBehavior;
    BottomAppBar bottomAppBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomAppBar = ((ServiceProviderMainActivity) getActivity()).bottomAppBar;
//        bottomAppBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparentWhite_99));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_provider_calendar_view, container, false);

        mMaterialCalendarView = mRootView.findViewById(R.id.calendarView);
        mMaterialCalendarView.addDecorator(new AllDaysDisabledDecorator());

        layoutBottomSheet = mRootView.findViewById(R.id.bottom_sheet);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.addBottomSheetCallback(new BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        return mRootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}