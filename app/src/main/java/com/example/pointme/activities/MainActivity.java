package com.example.pointme.activities;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;
import com.example.pointme.fragments.FavoritesFragment;
import com.example.pointme.fragments.MyBookingFragment;
import com.example.pointme.fragments.SearchFragment;
import com.example.pointme.fragments.SettingsFragment;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Booking;
import com.example.pointme.models.Event;
import com.example.pointme.fragments.CategoriesFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;
    private BottomNavigationView bottomNavigationView;
    public Toolbar toolbar;
    private TextView titleView;
    private TextView subTitleView;
    private ImageView backButton;

    private Event mEvent;
    private Appointment mAppointment;
    private Booking mBooking;

    private ArrayList<String> mDates;
    private ActionBar actionBar;

    private CategoriesFragment categoriesFragment;

    /* Bundle Tags */
    public static final String nameOfProvider = "nameOfProvider";
    public static final String profileInfoTag = "ProfileInfo";

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
//        mDatabase = FirebaseDatabase.getInstance().getReference();

//        titleView = findViewById(R.id.titleView);
//        subTitleView = findViewById(R.id.subTitleView);
//        backButton = findViewById(R.id.backButton);

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
       categoriesFragment = new CategoriesFragment();
       loadFragment(categoriesFragment, getString(R.string.categories));

       bottomNavigationView.setOnNavigationItemSelectedListener(
               new BottomNavigationView.OnNavigationItemSelectedListener() {
                   @Override
                   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                       switch (item.getItemId()) {
                           case R.id.categories:
                               Log.i(TAG, "Loading categories fragment");
                               loadFragment(categoriesFragment, getString(R.string.categories));
                               return true;
                           case R.id.favorites:
                               actionBar.show();
                               Log.i(TAG, "Loading favorites fragment");
                               FavoritesFragment favoritesFragment = new FavoritesFragment();
                               loadFragment(favoritesFragment, getString(R.string.favorites));
                               return true;
                           case R.id.search:
                               actionBar.show();
                               Log.i(TAG, "Loading favorites fragment");
                               SearchFragment searchFragment = new SearchFragment();
                               loadFragment(searchFragment, getString(R.string.search));
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

//    public void setTitle(String title) {
//        if (titleView != null) {
//            if (title.isEmpty()) {
//                titleView.setVisibility(View.INVISIBLE);
//                subTitleView.setText(getString(R.string.subtitle));
//                backButton.setVisibility(View.VISIBLE);
//            } else {
//                subTitleView.setText(getString(R.string.subtitle));
//                titleView.setText(title);
//                subTitleView.setVisibility(View.VISIBLE);
//                backButton.setVisibility(View.VISIBLE);
//            }
//        }
//    }
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
    }
}