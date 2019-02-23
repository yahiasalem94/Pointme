package com.example.pointme.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.pointme.R;
import com.example.pointme.fragments.CategoriesFragment;
import com.example.pointme.fragments.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class testActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String TAG = "testActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        bottomNavigationView = findViewById(R.id.navigation);
        CategoriesFragment fragment = new CategoriesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.your_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                // load fragment
                                CategoriesFragment fragment = new CategoriesFragment();
                                Log.d("categories", "hi0");
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
                                transaction.replace(R.id.your_placeholder, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                return true;
                            case R.id.favorites:
                                //TODO
                                return true;
                            case R.id.search:
                                //TODO
                                return true;
                            case R.id.booking:
                                //TODO
                                return true;
                            case R.id.more:
                                ProfileFragment frag = new ProfileFragment();
                                Log.d("categories", "hi0");
                                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                                trans.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
                                trans.replace(R.id.your_placeholder, frag);
                                trans.addToBackStack(null);
                                trans.commit();
                                return true;
                        }
                        return false;
                    }
                });
    }
}
