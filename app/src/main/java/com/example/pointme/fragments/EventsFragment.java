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

import com.example.pointme.constants.ServerResult;
import com.example.pointme.constants.Type;
import com.example.pointme.interfaces.ProfileAdapterCallback;
import com.example.pointme.interfaces.EventsFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.backendCommunications.DBCom;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.ProfileInfo;

import java.util.ArrayList;
import java.util.List;

import static com.example.pointme.decorator.CardViewAnimation.collapseView;
import static com.example.pointme.decorator.CardViewAnimation.expandView;

public class EventsFragment extends Fragment implements ProfileAdapterCallback, EventsFragmentDBInt, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private ProfileAdapter profileAdapter;
    private ProfileInfo profileInfo;
    /*Views*/
    private RecyclerView recyclerList;
    private Button book;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = (ProfileInfo) getArguments().getSerializable("ProfileInfo");
        } else {
            profileInfo = null;
        }
        profileAdapter = new ProfileAdapter(null, null,this, getActivity());

        DBCom.getSPEventsAndAppointments(this, profileInfo.getKey());
        //DBCom.getProfile(this, name);

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
            //loadFragment();
        }
    }

    public void loadFragment() {
        /* TODO Date Picker fragment is being loaded from here */
//        Log.d(TAG, "loading fragment");

    }
    @Override
    public void onBookPressed(Object object, @Type int type) {
        //loadFragment();
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProfileInfo", profileInfo);
        if(type == Type.EVENT){
            bundle.putSerializable("Event", (Event) object);
        }else{
            bundle.putSerializable("Appointment", (Appointment) object);
        }
        bundle.putInt("Type", type);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
            //info.setTitle(name);
            result.add(info);
        }

        return result;
    }

    @Override
    public void setSPEventsAndAppointments(@ServerResult int serverResult, ArrayList<Event> eventsList, ArrayList<Appointment> appointmentsList) {
        if(serverResult == ServerResult.SUCCESS){
            profileAdapter.newList(eventsList, appointmentsList);
            recyclerList.getAdapter().notifyDataSetChanged();
        }
    }
}
