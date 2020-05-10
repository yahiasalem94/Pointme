package com.example.pointme.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.pointme.decorator.DividerItemDecoration;
import com.example.pointme.interfaces.EventsFragmentDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.ProfileAdapter;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.Meeting;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.viewModels.AppointmentsViewModel;
import com.example.pointme.viewModels.AppointmentsViewModelFactory;
import com.example.pointme.viewModels.EventsViewModel;
import com.example.pointme.viewModels.EventsViewModelFactory;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.pointme.activities.MainActivity.APPOINTMENT;
import static com.example.pointme.activities.MainActivity.EVENT;
import static com.example.pointme.activities.MainActivity.MEETING;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.example.pointme.activities.MainActivity.TYPE;

public class EventsFragment extends Fragment implements ProfileAdapter.ProfileAdapterOnClickHandler {

    private final String TAG = EventsFragment.class.getSimpleName();

    private ProfileAdapter profileAdapter;
    private ServiceProvider profileInfo;

    private Appointment appointmentsModel;
    private Event eventsModel;
    private ArrayList<Meeting> meetings;
//    private ArrayList<Appointment> appointments;
//    private ArrayList<Event> events;

    private AppointmentsViewModel appointmentsViewModel;
    private EventsViewModel eventsViewModel;
    /*Views*/
    private RecyclerView recyclerList;
    private Button book;
    private NestedScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        meetings = new ArrayList<>();
        if (getArguments() != null) {
            profileInfo = getArguments().getParcelable(PROFILE_INFO);

            AppointmentsViewModelFactory appointmentsViewModelFactory = new AppointmentsViewModelFactory(profileInfo.getuID());
            appointmentsViewModel = new ViewModelProvider(EventsFragment.this, appointmentsViewModelFactory).get(AppointmentsViewModel.class);

            EventsViewModelFactory eventsViewModelFactory = new EventsViewModelFactory(profileInfo.getuID());
            eventsViewModel = new ViewModelProvider(EventsFragment.this, eventsViewModelFactory).get(EventsViewModel.class);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View mRootview = inflater.inflate(R.layout.fragment_events, parent, false);
        scrollView = mRootview.findViewById(R.id.scrollView);
        book = mRootview.findViewById(R.id.bookNow);
        recyclerList = mRootview.findViewById(R.id.card_view_list);

        profileAdapter = new ProfileAdapter(this, getActivity(), scrollView);
        setupRecyclerView();

        return mRootview;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerList.setLayoutManager(linearLayoutManager);
        recyclerList.addItemDecoration(new DividerItemDecoration(getActivity()));

        // Set data adapter.
        recyclerList.setAdapter(profileAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        ViewCompat.setNestedScrollingEnabled(recyclerList, false);

//
//        final GestureDetector gesture = new GestureDetector(getActivity(),
//                new GestureDetector.SimpleOnGestureListener() {
//
//                    @Override
//                    public boolean onDown(MotionEvent e) {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//                                           float velocityY) {
//                        Log.i(TAG, "onFling has been called!");
//                        final int SWIPE_MIN_DISTANCE = 120;
//                        final int SWIPE_MAX_OFF_PATH = 250;
//                        final int SWIPE_THRESHOLD_VELOCITY = 200;
//                        try {
//                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//                                return false;
//                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
//                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                                Log.i(TAG, "Right to Left");
//                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
//                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                                Log.i(TAG, "Left to Right");
//                            }
//                        } catch (Exception e) {
//                            // nothing
//                        }
//                        return super.onFling(e1, e2, velocityX, velocityY);
//                    }
//                });
//
//        recyclerList.setOnTouchListener((v, event) -> gesture.onTouchEvent(event));

        observeMeetings();

    }

    private void observeMeetings() {

        LiveData<QuerySnapshot> appointmentsLiveData = appointmentsViewModel.getDataSnapshotLiveData();
        LiveData<QuerySnapshot> eventsLiveData = eventsViewModel.getDataSnapshotLiveData();

        appointmentsLiveData.observe(getViewLifecycleOwner(), dataSnapshot -> {
            if (dataSnapshot != null) {
//                mProgressBar.setVisibility(View.INVISIBLE);
                for (int i = 0 ; i < dataSnapshot.getDocuments().size(); i++) {
                    appointmentsModel = dataSnapshot.getDocuments().get(i).toObject(Appointment.class);
                    meetings.add(appointmentsModel);
                }
                profileAdapter.setMeetings(meetings);
//                showDataView();
            } else {
//                mProgressBar.setVisibility(View.INVISIBLE);
//                showErrorMessage();
            }
        });

        eventsLiveData.observe(getViewLifecycleOwner(), dataSnapshot -> {
            if (dataSnapshot != null) {
//                mProgressBar.setVisibility(View.INVISIBLE);
                for (int i = 0 ; i < dataSnapshot.getDocuments().size(); i++) {
                    eventsModel = dataSnapshot.getDocuments().get(i).toObject(Event.class);
                    meetings.add(eventsModel);
                }
                profileAdapter.setMeetings(meetings);
//                showDataView();
            } else {
//                mProgressBar.setVisibility(View.INVISIBLE);
//                showErrorMessage();
            }
        });
    }

    @Override
    public void onClick(@Type int type, int position) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PROFILE_INFO, profileInfo);

        bundle.putParcelable(MEETING, meetings.get(position));
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
