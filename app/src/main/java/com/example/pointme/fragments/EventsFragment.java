package com.example.pointme.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

public class EventsFragment extends Fragment implements ProfileAdapterCallback, EventsFragmentDBInt {

    private static final String ARG_PARAM1 = "param1";
    private ProfileAdapter profileAdapter;
    private ProfileInfo profileInfo;
    private final String TAG = "EventFragment";
    /*Views*/
    private RecyclerView recyclerList;
    private Button book;
    private NestedScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = (ProfileInfo) getArguments().getSerializable("ProfileInfo");
        } else {
            profileInfo = null;
        }


        DBCom.getSPEventsAndAppointments(this, profileInfo.getKey());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerList = view.findViewById(R.id.card_view_list);
        recyclerList.setLayoutManager(linearLayoutManager);

        scrollView = view.findViewById(R.id.scrollView);
        book = view.findViewById(R.id.bookNow);

        profileAdapter = new ProfileAdapter(null, null,this, getActivity(), scrollView);

        // Set data adapter.
        recyclerList.setAdapter(profileAdapter);

        ViewCompat.setNestedScrollingEnabled(recyclerList, false);

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        Log.i(TAG, "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i(TAG, "Right to Left");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i(TAG, "Left to Right");
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        recyclerList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
    }

    @Override
    public void onBookPressed(Object object, @Type int type) {
        /* loadFragment */
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ProfileInfo", profileInfo);
        if(type == Type.EVENT){
            bundle.putParcelable("Event", (Event) object);
        }else{
            bundle.putParcelable("Appointment", (Appointment) object);
        }
        bundle.putInt("Type", type);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setSPEventsAndAppointments(@ServerResult int serverResult, ArrayList<Event> eventsList, ArrayList<Appointment> appointmentsList) {
        if(serverResult == ServerResult.SUCCESS){
            profileAdapter.newList(eventsList, appointmentsList);
            recyclerList.getAdapter().notifyDataSetChanged();
        }
    }
}
