package com.example.pointme.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pointme.Interfaces.ProfileAdapterCallback;
import com.example.pointme.Interfaces.ProfileFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.backendCommunications.DBCom;
import com.example.pointme.models.Event;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;
import java.util.List;

import static com.example.pointme.decorator.CardViewAnimation.collapseView;
import static com.example.pointme.decorator.CardViewAnimation.expandView;

public class EventsFragment extends Fragment implements ProfileAdapterCallback, ProfileFragmentDBInt, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private ProfileAdapter profileAdapter;
    private String name;
    /*Views*/
    private RecyclerView recyclerList;
    private Button book;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
        } else {
            name = "Yahia";
        }
        profileAdapter = new ProfileAdapter(null, this, getActivity());

        DBCom.getProfile(this, name);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerList = view.findViewById(R.id.card_view_list);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(linearLayoutManager);
        // recyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation()));

        book = view.findViewById(R.id.bookNow);
        //  book.setOnClickListener(this);
        // Set data adapter.
        recyclerList.setAdapter(profileAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == book) {
            loadFragment();
        }
    }

    public void loadFragment() {
        /* TODO Date Picker fragment is being loaded from here */
//        Log.d(TAG, "loading fragment");
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, name);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onMethodCallback() {
        loadFragment();
    }

    private void toggleCardViewnHeight(View v, int minHeight, int height) {

        if (v.getHeight() == minHeight) {
            // expand
            expandView(v, height); //'height' is the height of screen which we have measured already.
        } else {
            // collapse
            collapseView(v, minHeight);

        }
    }

    private List<ProfileInfo> createList() {

        List<ProfileInfo> result = new ArrayList<>();
        ArrayList<String> y = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            ProfileInfo info = new ProfileInfo();
            info.setName("yahia");
            info.setTitle(name);
            result.add(info);
        }

        return result;
    }

    @Override
    public void setProfile(ProfileInfo profile, ArrayList<Event> eventsList) {
        /*TODO: Can be removed name is already passed on from previous activity*/
        Log.d("ramy", eventsList.get(0).getName());
        Log.d("ramy", eventsList.get(0).getKey());
        Event e = new Event();
        e.setName("workshop");
        e.setDesc("this is a workshop");
        Event e1 = new Event();
        e1.setName("workshop");
        e1.setDesc("this is a workshop");
        eventsList.add(e);
        eventsList.add(e1);
        profileAdapter.newList(eventsList);
        recyclerList.getAdapter().notifyDataSetChanged();
    }
}
