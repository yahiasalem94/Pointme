package com.example.pointme.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.R;
import com.example.pointme.fragments.FavoritesFragment;
import com.example.pointme.fragments.MyBookingFragment;
import com.example.pointme.fragments.SettingsFragment;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Booking;
import com.example.pointme.models.Event;
import com.example.pointme.fragments.CategoriesFragment;
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
    private TextView titleView;
    private TextView subTitleView;
    private ImageView backButton;

    private Event mEvent;
    private Appointment mAppointment;
    private Booking mBooking;

    private ArrayList<String> mDates;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       /* if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        titleView = findViewById(R.id.titleView);
        subTitleView = findViewById(R.id.subTitleView);
        backButton = findViewById(R.id.backButton);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        bottomNavigationView = findViewById(R.id.navigation);

       /* if (mAuth.getCurrentUser() == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {*/
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.categories:
                                    Log.i(TAG, "Loading categories fragment");
                                    CategoriesFragment categoriesFragment = new CategoriesFragment();
                                    loadFragment(categoriesFragment, getString(R.string.categories));
                                    return true;
                                case R.id.favorites:
                                    actionBar.show();
                                    Log.i(TAG, "Loading favorites fragment");
                                    FavoritesFragment favoritesFragment = new FavoritesFragment();
                                    loadFragment(favoritesFragment, getString(R.string.favorites));
                                    return true;
                                case R.id.search:
                                    //TODO
                                    return true;
                                case R.id.booking:
                                    actionBar.show();
                                    Log.i(TAG, "Loading booking fragment");
                                    MyBookingFragment bookingFragment = new MyBookingFragment();
                                    loadFragment(bookingFragment, getString(R.string.booking));
                                    return true;
                                case R.id.more:
                                    actionBar.show();
                                    Log.i(TAG, "Loading settings fragment");
                                    SettingsFragment settingsFragment = new SettingsFragment();
                                    loadFragment(settingsFragment, "SETTINGS");
                                    return true;
                            }
                            return false;
                        }
                    });
        }
//    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setTitle(String title) {
        if (titleView != null) {
            if (title.isEmpty()) {
                titleView.setVisibility(View.INVISIBLE);
                subTitleView.setText(getString(R.string.subtitle));
                backButton.setVisibility(View.VISIBLE);
            } else {
                subTitleView.setText(getString(R.string.subtitle));
                titleView.setText(title);
                subTitleView.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
            }
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

    public void loadFragment(Fragment fragment, String title) {
        // load fragment
        Log.d(TAG, "loading fragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        titleView.setText(title);

        if (title.equals(getString(R.string.categories))) {
            backButton.setVisibility(View.INVISIBLE);
            subTitleView.setVisibility(View.INVISIBLE);
        } else {
            backButton.setVisibility(View.VISIBLE);
            subTitleView.setVisibility(View.VISIBLE);
            subTitleView.setText(getString(R.string.subtitle));
        }
    }
}