package com.example.pointme.fragments;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.pointme.Interfaces.AdapterCallback;
import com.example.pointme.Interfaces.ProfileFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.adapters.ProfileEventInfo;
import com.example.pointme.backend.DBCom;
import com.example.pointme.models.Event;
import com.example.pointme.models.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements AdapterCallback, ProfileFragmentDBInt {

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

        DBCom.getProfile(this, title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RecyclerView list = view.findViewById(R.id.card_view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        // Set data adapter.
        list.setAdapter(adapter);
    }

    @Override
    public void onMethodCallback(String title) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1,title);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private List<ProfileEventInfo> createList() {

        List<ProfileEventInfo> result = new ArrayList<ProfileEventInfo>();
        ArrayList<String> y = new ArrayList<String>();

        for (int i = 0; i < 5; i++)
        {
            ProfileEventInfo info = new ProfileEventInfo();
            info.setName("yahia");
            info.setTitle(title);
            result.add(info);
        }

        return result;
    }

    @Override
    public void setProfile(Profile profile, ArrayList<Event> eventsList) {
        Log.d("ramy", profile.getEmail());
        Log.d("ramy", profile.getIg());
        Log.d("ramy", profile.getImage());
        Log.d("ramy", profile.getName());
        Log.d("ramy", profile.getTel());
        Log.d("ramy", eventsList.get(0).getName());
        Log.d("ramy", eventsList.get(0).getKey());
    }
}