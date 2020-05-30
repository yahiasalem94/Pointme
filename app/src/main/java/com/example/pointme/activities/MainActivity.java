package com.example.pointme.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.pointme.utils.ConnectionLiveData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.example.pointme.R;
import com.example.pointme.fragments.FavoritesFragment;
import com.example.pointme.fragments.MyBookingFragment;
import com.example.pointme.fragments.SettingsFragment;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Booking;
import com.example.pointme.models.Event;
import com.example.pointme.fragments.CategoriesFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;
    private BottomNavigationView bottomNavigationView;
    public Toolbar toolbar;
    public ToggleButton favoritesButton;
    private Event mEvent;
    private Appointment mAppointment;
    private Booking mBooking;

    private ArrayList<String> mDates;
    private ConnectionLiveData connectionLiveData;

    private CategoriesFragment categoriesFragment;

    private CardView noInternetView;
    private FrameLayout container;
    /* Bundle Tags */
    public static final String NAME_OF_PROVIDER = "nameOfProvider";
    public static final String PROFILE_INFO = "profileInfo";
    public static final String MEETING = "meeting";
    public static final String EVENT = "event";
    public static final String APPOINTMENT = "appointment";
    public static final String TYPE = "type";
    public static final String PROFILE_UID = "profileUid";
    public static final String BOOKING = "booking";
    public static final String CONFIRM_DATE = "ConfirmDate";
    public static final String CONFIRM_TIME = "ConfirmTime";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noInternetView = findViewById(R.id.noInternetCV);
        container = findViewById(R.id.frame_container);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        favoritesButton = findViewById(R.id.button_favorite);
        categoriesFragment = new CategoriesFragment();
        loadFragment(categoriesFragment, getString(R.string.categories));
        connectionLiveData = new ConnectionLiveData(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "Connection changed " + aBoolean);
                if (!aBoolean) {
                    noInternetView.setVisibility(View.VISIBLE);
                    container.setVisibility(View.GONE);
                } else {
                    noInternetView.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void loadFragment(Fragment fragment, String title) {
        // load fragment
        Log.d(TAG, "loading fragment");
//        toolbar.setTitle(title);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.categories:
                Log.i(TAG, "Loading categories fragment");
                loadFragment(categoriesFragment, getString(R.string.categories));
                return true;
            case R.id.favorites:
                Log.i(TAG, "Loading favorites fragment");
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                loadFragment(favoritesFragment, getString(R.string.favorites));
                return true;
//            case R.id.search:
//                Log.i(TAG, "Loading favorites fragment");
//                SearchFragment searchFragment = new SearchFragment();
//                loadFragment(searchFragment, getString(R.string.search));
//                return true;
            case R.id.booking:
                Log.i(TAG, "Loading booking fragment");
                MyBookingFragment bookingFragment = new MyBookingFragment();
                loadFragment(bookingFragment, getString(R.string.booking));
                return true;
            case R.id.more:
                Log.i(TAG, "Loading settings fragment");
                SettingsFragment settingsFragment = new SettingsFragment();
                loadFragment(settingsFragment, "SETTINGS");
                return true;
        }
        return false;
    }
}