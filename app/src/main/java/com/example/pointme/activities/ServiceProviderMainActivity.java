package com.example.pointme.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.pointme.fragments.ProviderCalendarView;
import com.example.pointme.fragments.ProvidersProfileFragment;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.ConnectionLiveData;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
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

import static com.example.pointme.activities.MainActivity.PROFILE_INFO;


public class ServiceProviderMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = ServiceProviderMainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    //    private DatabaseReference mDatabase;
    private BottomNavigationView bottomNavigationView;
    public Toolbar toolbar;
    private Event mEvent;
    private Appointment mAppointment;
    private Booking mBooking;

    private ArrayList<String> mDates;
    private ConnectionLiveData connectionLiveData;

    private CardView noInternetView;
    private FrameLayout container;
    public BottomAppBar bottomAppBar;
    private ProviderCalendarView providersCalendarFragment;

    ServiceProvider provider;
    /* Bundle Tags */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_main);
        noInternetView = findViewById(R.id.noInternetCV);
        container = findViewById(R.id.frame_container);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
         provider = new ServiceProvider("test@test.com", "Lina Essam", "01225189191",
                null, "https://firebasestorage.googleapis.com/v0/b/pointme-dbd0b.appspot.com/o/Users%2F7SePfePNkWbTVCVgG0NoXtl9IKI3.jpg?alt=media&token=6b731b9e-3c71-4bb6-9db1-58bbdbb815f2"
        ,null, null,"Yoga", 3,4, 2, 5,10 );
        providersCalendarFragment = new ProviderCalendarView();
        loadFragment(providersCalendarFragment);
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
//
    public void loadFragment(Fragment fragment) {
        // load fragment
        Log.d(TAG, "loading fragment");
        Bundle bundle = new Bundle();
        bundle.putParcelable(PROFILE_INFO, provider);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
//
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.booking:
                Log.i(TAG, "Loading favorites fragment");

                loadFragment(providersCalendarFragment);
                return true;
            case R.id.action_needed:
                Log.i(TAG, "Loading booking fragment");
                MyBookingFragment bookingFragment = new MyBookingFragment();
                loadFragment(bookingFragment);
                return true;
            case R.id.profile:
                Log.i(TAG, "Loading categories fragment");
                ProvidersProfileFragment profileFragment = new ProvidersProfileFragment();
                loadFragment(profileFragment);
                return true;
            case R.id.more:
                Log.i(TAG, "Loading settings fragment");
                SettingsFragment settingsFragment = new SettingsFragment();
                loadFragment(settingsFragment);
                return true;
        }
        return false;
    }
}