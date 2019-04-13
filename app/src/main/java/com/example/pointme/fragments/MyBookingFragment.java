package com.example.pointme.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.adapters.FavoritesAdapter;
import com.example.pointme.models.ProvidersInfo;

import java.util.ArrayList;
import java.util.List;

public class MyBookingFragment extends Fragment implements RecyclerViewClickListener {

    private String title = "Bookings";
    private Toolbar toolbar;
    private FavoritesAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private String TAG = "BookingsFragment";
    /*views*/
    RecyclerView list;
    LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new FavoritesAdapter(createList(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_my_booking, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);

        list = view.findViewById(R.id.cardList);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);

        // Set data adapter.
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Bookings");
    }

    private List<ProvidersInfo> createList() {

        List<ProvidersInfo> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ProvidersInfo info = new ProvidersInfo();
            info.setName("yahia");
            result.add(info);
        }

        return result;
    }
}