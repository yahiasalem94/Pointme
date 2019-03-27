package com.example.pointme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Range;
import android.view.MenuItem;

import com.example.pointme.R;
import com.example.pointme.backend.DBCom;
import com.example.pointme.fragments.FavoritesFragment;
import com.example.pointme.fragments.MyBookingFragment;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Booking;
import com.example.pointme.models.Event;
import com.example.pointme.fragments.CategoriesFragment;
import com.example.pointme.models.Helper;
import com.example.pointme.models.PointmeDate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private Event mEvent;
    private Appointment mAppointment;
    private Booking mBooking;

    private ArrayList<String> mDates;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        bottomNavigationView = findViewById(R.id.navigation);

        if (mAuth.getCurrentUser() == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            /*ArrayList<String> trial = new ArrayList<>();
            String date1 = "201903061830";
            PointmeDate date2 = PointmeDate.StringToDate(date1);
            date2.addDuration("0130");
            trial.add(date2.toString());
            Booking booking = new Booking("loca", "01234", "56789", "Event", false, false, trial);
            DBCom.uploadBooking1(null, booking);
            DBCom.fetchBooking("-L_JcghK26gFRGqo0pKM");
            DBCom.fetchBookingByCreatorID("01234");*/
            Range<String> x = new Range<>("1700", "9999");
            //Log.d("ramy", "yeah5" + x.intersect("1800", "2200").toString());
            ArrayList<String> arrayList = new ArrayList<>();
            String db = "201903171200140015001700";
            String app = "Su0900143016002200";
            String wb = "Su1200140015001700";
            arrayList = Helper.getBookingSlots(db, null, app, "0200", "0030");
            //Log.d("ramy", "yeah" + arrayList);
            DBCom.getProfile(null, "Farah Nofal");
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.home:
                                    Log.i(TAG, "Loading categories fragment");
                                    actionBar.hide();
                                    CategoriesFragment categoriesFragment = new CategoriesFragment();
                                    loadFragment(categoriesFragment, "Home");
                                    return true;
                                case R.id.favorites:
                                    actionBar.show();
                                    Log.i(TAG, "Loading favorites fragment");
                                    FavoritesFragment favoritesFragment = new FavoritesFragment();
                                    loadFragment(favoritesFragment, "Favorites");
                                    return true;
                                case R.id.search:
                                    //TODO
                                    return true;
                                case R.id.booking:
                                    actionBar.show();
                                    Log.i(TAG, "Loading booking fragment");
                                    MyBookingFragment bookingFragment = new MyBookingFragment();
                                    loadFragment(bookingFragment, "Bookings");
                                    return true;
                                case R.id.more:
                                    return true;
                            }
                            return false;
                        }
                    });
        }
    }

   /* @Override
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
    }*/
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
           case android.R.id.home:
               onBackPressed();
       }
       return super.onOptionsItemSelected(item);
   }

    public void loadFragment(Fragment fragment, String title) {
        // load fragment
        Log.d(TAG, "loading fragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportActionBar().setTitle(title);
    }
}