package com.example.pointme.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pointme.fragments.NumberPickerFragment;

public class TimePickerAdapter extends FragmentPagerAdapter {

    private NumberPickerFragment startTime;
    private NumberPickerFragment endTime;

    public TimePickerAdapter(FragmentManager fm){
        super(fm);
        startTime = new NumberPickerFragment();
        endTime = new NumberPickerFragment();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return NumberPickerFragment.newInstance(1);
            case 1:
                return NumberPickerFragment.newInstance(2);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
