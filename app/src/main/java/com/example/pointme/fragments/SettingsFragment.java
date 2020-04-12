package com.example.pointme.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.pointme.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);
    }
}
