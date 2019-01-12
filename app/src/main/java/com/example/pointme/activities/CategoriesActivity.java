package com.example.pointme.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.example.pointme.R;
import com.example.pointme.adapters.CategoriesAdapter;
import com.example.pointme.classes.CategoriesItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private List<CategoriesItem> categoriesList = null;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ServiceProviders");
        initializeList();

        // Create the recyclerview.
        RecyclerView categoriesView = (RecyclerView)findViewById(R.id.card_view_recycler_list);
        // Create the grid layout manager with 2 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        // Set layout manager.
        categoriesView.setLayoutManager(gridLayoutManager);

        // Create car recycler view data adapter with car item list.
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categoriesList, this);
        // Set data adapter.
        categoriesView.setAdapter(categoriesAdapter);

    }

    /* Initialise car items in list. */
    private void initializeList()
    {
        if(categoriesList == null)
        {
            categoriesList = new ArrayList<CategoriesItem>();
            categoriesList.add(new CategoriesItem("Crossfit", R.drawable.crossfit));
            categoriesList.add(new CategoriesItem("Yoga", R.drawable.yoga));
            categoriesList.add(new CategoriesItem("Hairdresser", R.drawable.hairdresser));
            categoriesList.add(new CategoriesItem("Makeup Artists", R.drawable.makeup));
            categoriesList.add(new CategoriesItem("Swimming", R.drawable.swimming));
        }
    }
}