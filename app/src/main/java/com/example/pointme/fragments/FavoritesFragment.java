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

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.R;
import com.example.pointme.adapters.FavoritesAdapter;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements AdapterCallback {

    private Toolbar toolbar;
    private FavoritesAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private String title;

    private String TAG = "FavoriteFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        } else {
            title = "Pointme";
        }

        adapter = new FavoritesAdapter(createList(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_favorites, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorites");

        RecyclerView list = view.findViewById(R.id.cardList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        // Set data adapter.
        list.setAdapter(adapter);
    }

    @Override
    public void onMethodCallback(String title) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Favorites");
    }

    private List<ProfileInfo> createList() {

        List<ProfileInfo> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            ProfileInfo info = new ProfileInfo();
            info.setName("yahia" + i);
            info.setTitle(title);
            result.add(info);
        }

        return result;
    }
}