package com.example.pointme.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.R;
import com.example.pointme.adapters.CategoriesAdapter;
import com.example.pointme.adapters.CategoriesItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoriesFragment extends Fragment implements AdapterCallback {

    private Toolbar toolbar;

    private List<CategoriesItem> categoriesList = null;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private CategoriesAdapter categoriesAdapter;
    private HashMap<String, ArrayList<String>> serviceProviders;
    private String TAG = "CategoriesFragment";
    private static final String ARG_PARAM1 = "param1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initializeList();

        mDatabase.child("ServiceProviders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceProviders = (HashMap<String, ArrayList<String>>)dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        categoriesAdapter = new CategoriesAdapter(categoriesList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_categories, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the recyclerview.
        RecyclerView categoriesView = view.findViewById(R.id.card_view_recycler_list);
        // Create the grid layout manager with 2 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        categoriesView.setLayoutManager(gridLayoutManager);

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


    @Override
    public void onMethodCallback(String title) {
        ListOfServiceProvidersFragment fragment = new ListOfServiceProvidersFragment();
        Log.d(TAG, "hi0");
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1,title);
        bundle.putSerializable("Hashmap", serviceProviders);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}