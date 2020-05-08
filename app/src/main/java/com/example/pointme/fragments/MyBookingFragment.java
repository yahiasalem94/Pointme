package com.example.pointme.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pointme.adapters.BookingsAdapter;
import com.example.pointme.decorator.LinearSpacesItemDecoration;
import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.models.Booking;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.Helper;
import com.example.pointme.viewModels.BookingsViewModel;
import com.example.pointme.viewModels.BookingsViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyBookingFragment extends Fragment implements RecyclerViewClickListener, View.OnClickListener {

    private String title = "Bookings";
    private String currentUser;
    private BookingsAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private String TAG = "BookingsFragment";
    private boolean isPastBookings = false;
    /*views*/
    RecyclerView list;
    private LinearLayout upcomingLinearLayout;
    private LinearLayout pastLinearLayout;
    private TextView upcompingTv;
    private TextView pastTv;

    private Booking bookingDetailsModel;
    private BookingsViewModel bookingsViewModel;
    private ArrayList<Booking> bookings, upcomingBookings, pastBookings;

    private CalendarDay dateToday;

    public MyBookingFragment() {
        bookings = new ArrayList<>();
        upcomingBookings = new ArrayList<>();
        pastBookings = new ArrayList<>();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dateToday = CalendarDay.from(year, month, day);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapter = new BookingsAdapter( this);
        BookingsViewModelFactory bookingsViewModelFactory = new BookingsViewModelFactory(currentUser);
        bookingsViewModel = new ViewModelProvider(MyBookingFragment.this, bookingsViewModelFactory).get(BookingsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View mRootview = inflater.inflate(R.layout.fragment_my_booking, parent, false);
        list = mRootview.findViewById(R.id.bookings_recycler_view);
        upcompingTv = mRootview.findViewById(R.id.upcomingTv);
        pastTv = mRootview.findViewById(R.id.pastTv);
        upcomingLinearLayout = mRootview.findViewById(R.id.upcomingLinearLayout);
        pastLinearLayout = mRootview.findViewById(R.id.pastLinearLayout);

        upcomingLinearLayout.setOnClickListener(this);
        upcomingLinearLayout.setClickable(false);

        pastLinearLayout.setOnClickListener(this);
        pastLinearLayout.setClickable(true);

        setupRecyclerView();
        return mRootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        observeMeetings();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        list.addItemDecoration(new LinearSpacesItemDecoration( 50));
        // Set data adapter.
        list.setAdapter(adapter);
    }
    @Override
    public void onClick(String title) {

    }

    @Override
    public void onClickPI(ServiceProvider profileInfo) {

    }

    private void observeMeetings() {

        LiveData<QuerySnapshot> bookingsLiveData = bookingsViewModel.getDataSnapshotLiveData();

        bookingsLiveData.observe(getViewLifecycleOwner(), dataSnapshot -> {
            if (dataSnapshot != null) {
////                mProgressBar.setVisibility(View.INVISIBLE);
                for (int i = 0 ; i < dataSnapshot.getDocuments().size(); i++) {
                    bookingDetailsModel = dataSnapshot.getDocuments().get(i).toObject(Booking.class);
                    if (Helper.stringToDate(bookingDetailsModel.getBookingDetails().get(0).getDate()).isBefore(dateToday)) {
                        pastBookings.add(bookingDetailsModel);
                    } else {
                        upcomingBookings.add(bookingDetailsModel);
                    }
                }

                if (isPastBookings) {
                 adapter.newList(pastBookings, isPastBookings);
                } else {
                    adapter.newList(upcomingBookings, isPastBookings);
                }
//                profileAdapter.setMeetings(meetings);
////                showDataView();
//            } else {
////                mProgressBar.setVisibility(View.INVISIBLE);
////                showErrorMessage();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Bookings");
    }

    public void upcomingClick() {
        isPastBookings = false;
        pastLinearLayout.setBackgroundResource(0);
        upcomingLinearLayout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.capsule_white_background));
        upcompingTv.setTextColor(Color.BLACK);
        pastTv.setTextColor(Color.WHITE);
        adapter.newList(upcomingBookings, isPastBookings);

        pastLinearLayout.setClickable(true);
        upcomingLinearLayout.setClickable(false);
    }


    public void pastClick() {
        isPastBookings = true;
        upcomingLinearLayout.setBackgroundResource(0);
        pastLinearLayout.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.capsule_white_background));
        pastTv.setTextColor(Color.BLACK);
        upcompingTv.setTextColor(Color.WHITE);
        adapter.newList(pastBookings, isPastBookings);

        pastLinearLayout.setClickable(false);
        upcomingLinearLayout.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upcomingLinearLayout) {
            upcomingClick();
        } else {
            pastClick();
        }
    }
}