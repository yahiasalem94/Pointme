package com.example.pointme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pointme.R;
import com.example.pointme.classes.AllDaysDisabledDecorator;
import com.example.pointme.classes.Appointment;
import com.example.pointme.classes.Booking;
import com.example.pointme.classes.Constants;
import com.example.pointme.classes.DayEnableDecorator;
import com.example.pointme.classes.Event;
import com.example.pointme.classes.PointmeDate;
import com.example.pointme.classes.ScheduleHelper;
import com.example.pointme.fragments.DatePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Event mEvent;
    private Appointment mAppointment;
    private Booking mBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mAuth.getCurrentUser() == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            ArrayList<PointmeDate> pointmeDates = new ArrayList<>();
            pointmeDates.add(new PointmeDate(11,12,2018,8,15));
            pointmeDates.add(new PointmeDate(11,2,2018,8,0));
            pointmeDates.add(new PointmeDate(9,12,2018,9,15));
            pointmeDates.add(new PointmeDate(31,12,2018,9,15));
            pointmeDates.add(new PointmeDate(1,1,2019,9,15));
            pointmeDates.add(new PointmeDate(5,1,2019,9,15));
            pointmeDates.add(new PointmeDate(6,1,2019,9,15));
            pointmeDates.add(new PointmeDate(8,1,2019,9,15));
            pointmeDates.add(new PointmeDate(8,1,2019,8,30));
            pointmeDates.add(new PointmeDate(8,1,2019,7,15));
            pointmeDates.add(new PointmeDate(8,1,2019,8,15));
            pointmeDates.add(new PointmeDate(8,1,2019,4,0));
            pointmeDates.add(new PointmeDate(8,1,2019,2,30));
            pointmeDates.add(new PointmeDate(8,1,2019,1,30));
            pointmeDates.add(new PointmeDate(8,1,2019,2,0));
            pointmeDates.add(new PointmeDate(8,1,2019,3,0));
            pointmeDates.add(new PointmeDate(8,1,2019,10,0));
            pointmeDates.add(new PointmeDate(8,1,2019,10,30));
            pointmeDates.add(new PointmeDate(8,2,2019,9,15));

            ArrayList<String> arrayDates= new ArrayList<>();
            for(PointmeDate date: pointmeDates){
                arrayDates.add(date.toString());
            }

            Collections.sort(arrayDates);

            mEvent = new Event("RamyEvent2", "Big Event", "No location", 20, 0, 100, 1.5, true, true, false, arrayDates);
            mAppointment = new Appointment("RamyApp", "niceee", "no loc", 25, 0, 150, 1, 0.5, true, true, false, false, arrayDates);
            mBooking = new Booking("No loc", false, false, arrayDates);

            //ScheduleHelper.uploadEvent(mAuth, mDatabase, mEvent);
            //ScheduleHelper.uploadAppointment(mAuth, mDatabase, mAppointment);
            ScheduleHelper.uploadBooking(mAuth, mDatabase, Constants.APPOINTMENTTYPE, "RamyApp", mBooking);

            mDatabase.child(Constants.APPOINTMENTS).child(mAuth.getCurrentUser().getUid()).orderByChild("RamyApp").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnap: dataSnapshot.getChildren()){
                        String key = dataSnap.getKey();
                        mDatabase.child(Constants.DATES).child(mAuth.getCurrentUser().getUid()).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<String> dates = new ArrayList<>();
                                for (DataSnapshot dataSnap: dataSnapshot.getChildren()){
                                    dates.add(dataSnap.getKey());
                                    Log.d("Ramy", dataSnap.getKey());
                                }
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("Dates", dates);
                                DatePickerFragment fragment = new DatePickerFragment();
                                fragment.setArguments(bundle);
                                loadFragment(fragment);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}