package com.example.pointme.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.R;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.adapters.ProfileEventInfo;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements AdapterCallback {

    private Toolbar toolbar;
    private ProfileAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        } else {
            title = "Pointme";
        }

        adapter = new ProfileAdapter(createList(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_favorites, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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

    private List<ProfileEventInfo> createList() {

        List<ProfileEventInfo> result = new ArrayList<ProfileEventInfo>();
        ArrayList<String> y = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            ProfileEventInfo info = new ProfileEventInfo();
            info.setName("yahia");
            info.setTitle(title);
            result.add(info);
        }

        return result;
    }
}