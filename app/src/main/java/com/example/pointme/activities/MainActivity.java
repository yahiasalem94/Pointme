package com.example.pointme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pointme.R;
import com.example.pointme.classes.Appointment;
import com.example.pointme.classes.Booking;
import com.example.pointme.classes.Constants;
import com.example.pointme.classes.Event;
import com.example.pointme.classes.PointmeDate;
import com.example.pointme.classes.ScheduleHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


public class MainActivity extends AppCompatActivity {

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
            PointmeDate date1 = new PointmeDate(11,12,2018,8,15);
            PointmeDate date2 = new PointmeDate(11,2,2018,8,0);
            PointmeDate date3 = new PointmeDate(9,12,2018,9,15);
            ArrayList<String> arrayDates= new ArrayList<>();
            arrayDates.add(date1.toString());
            arrayDates.add(date2.toString());
            arrayDates.add(date3.toString());

            Collections.sort(arrayDates);

            mEvent = new Event("RamyEvent2", "Big Event", "No location", 20, 0, 100, 1.5, true, true, false, arrayDates);
            mAppointment = new Appointment("RamyApp", "niceee", "no loc", 25, 0, 150, 1, 0.5, true, true, false, false, arrayDates);
            mBooking = new Booking("No loc", false, false, arrayDates);

            ScheduleHelper.uploadEvent(mAuth, mDatabase, mEvent);
            ScheduleHelper.uploadAppointment(mAuth, mDatabase, mAppointment);
            ScheduleHelper.uploadBooking(mAuth, mDatabase, Constants.APPOINTMENTTYPE, "RamyApp", mBooking);
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
}