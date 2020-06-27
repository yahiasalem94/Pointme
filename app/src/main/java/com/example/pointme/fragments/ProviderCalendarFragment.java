package com.example.pointme.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pointme.R;
import com.example.pointme.adapters.ProviderCalendarAdapter;
import com.example.pointme.decorator.AllDaysDisabledDecorator;
import com.example.pointme.decorator.DayEnableDecorator;
import com.example.pointme.decorator.LinearBottomSpacesItemDecoration;
import com.example.pointme.decorator.ProvideDayEnableDecorator;
import com.example.pointme.models.Booking;
import com.example.pointme.models.BookingDetails;
import com.example.pointme.models.Meeting;
import com.example.pointme.utils.Helper;
import com.example.pointme.viewModels.BookingsViewModel;
import com.example.pointme.viewModels.BookingsViewModelFactory;
import com.example.pointme.viewModels.ProvidersBookingViewModel;
import com.example.pointme.viewModels.ProvidersBookingViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.pointme.constants.Constants.WEEKLY_BASED;


public class ProviderCalendarFragment extends Fragment implements OnDateSelectedListener, View.OnClickListener {

    private static final String TAG = ProviderCalendarFragment.class.getSimpleName();

    private MaterialCalendarView mMaterialCalendarView;
    private RecyclerView todaysListRecyclerView;
    private LinearLayout layoutBottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private TextView dateTodayTV;
    private ImageView previousDayIV;
    private ImageView nextDayIV;

    private ProviderCalendarAdapter mAdapter;

    /* Models */
    private Booking bookingDetailsModel;
    private ArrayList<Meeting> meetings = new ArrayList<>();

    /* ViewModels */
    private ProvidersBookingViewModel providersBookingsViewModel;

    private ArrayList<Booking> bookings = new ArrayList<>();
    private ArrayList<Booking> todaysBookings  = new ArrayList<>();
    private ArrayList<LocalDate> dates = new ArrayList<>();
    private CalendarDay dateToday;
    private boolean isPastBookings = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProvidersBookingViewModelFactory bookingsViewModelFactory = new ProvidersBookingViewModelFactory("7SePfePNkWbTVCVgG0NoXtl9IKI3");
        providersBookingsViewModel = new ViewModelProvider(ProviderCalendarFragment.this, bookingsViewModelFactory).get(ProvidersBookingViewModel.class);

        dateToday = CalendarDay.today();
        mAdapter = new ProviderCalendarAdapter(Objects.requireNonNull(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_provider_calendar_view, container, false);

        mMaterialCalendarView = mRootView.findViewById(R.id.calendarView);
        mMaterialCalendarView.addDecorator(new AllDaysDisabledDecorator());
        mMaterialCalendarView.setOnDateChangedListener(this);

        todaysListRecyclerView = mRootView.findViewById(R.id.provider_calendar_recyclerview);
        layoutBottomSheet = mRootView.findViewById(R.id.bottom_sheet);
        dateTodayTV = mRootView.findViewById(R.id.date_todayTv);
        previousDayIV = mRootView.findViewById(R.id.previous_dayIv);
        nextDayIV = mRootView.findViewById(R.id.next_dayIv);

        previousDayIV.setOnClickListener(this);
        nextDayIV.setOnClickListener(this);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.addBottomSheetCallback(new BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    mMaterialCalendarView.setVisibility(View.INVISIBLE);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mMaterialCalendarView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        setupRecyclerView();
        return mRootView;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        todaysListRecyclerView.setLayoutManager(linearLayoutManager);
        todaysListRecyclerView.addItemDecoration(new LinearBottomSpacesItemDecoration( 50));
        // Set data adapter.
        todaysListRecyclerView.setAdapter(mAdapter);
    }

    private void handleAppointments() {

        for (int i = 0; i < bookings.size(); i++) {
            for (int j = 0; j < bookings.get(i).getBookingDetails().size(); j++) {
                BookingDetails details = bookings.get(i).getBookingDetails().get(j);
                CalendarDay date = Helper.stringToDate(details.getDate());
                dates.add(date.getDate());
            }
        }
        mMaterialCalendarView.addDecorator(new ProvideDayEnableDecorator(dates));
        setDate(dateToday);
    }

    private void setDate(CalendarDay date) {
        mMaterialCalendarView.setDateSelected(date, true);
        dateTodayTV.setText(dateToday.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        mAdapter.setBookings(getBookingsOnSelectedDay(date));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        observeMeetings();
    }

    private void observeMeetings() {

        LiveData<QuerySnapshot> bookingsLiveData = providersBookingsViewModel.getDataSnapshotLiveData();

        bookingsLiveData.observe(getViewLifecycleOwner(), dataSnapshot -> {
            if (dataSnapshot != null) {
////                mProgressBar.setVisibility(View.INVISIBLE);
                for (int i = 0 ; i < dataSnapshot.getDocuments().size(); i++) {
                    bookingDetailsModel = dataSnapshot.getDocuments().get(i).toObject(Booking.class);
                    bookings.add(bookingDetailsModel);
//                    if (Helper.stringToDate(bookingDetailsModel.getBookingDetails().get(0).getDate()).isBefore(dateToday)) {
//                        pastBookings.add(bookingDetailsModel);
//                    } else {
//                        upcomingBookings.add(bookingDetailsModel);
//                    }
                }
                handleAppointments();
//                if (isPastBookings) {
//                    mAdapter.setMeetings(pastBookings, isPastBookings);
//                } else {
//                    mAdapter.setMeetings(upcomingBookings, isPastBookings);
//                }
//                profileAdapter.setMeetings(meetings);
////                showDataView();
//            } else {
////                mProgressBar.setVisibility(View.INVISIBLE);
////                showErrorMessage();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        dateToday = date;
        dateTodayTV.setText(dateToday.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        mAdapter.setBookings(getBookingsOnSelectedDay(date));
    }

    private ArrayList<Booking> getBookingsOnSelectedDay(CalendarDay date) {
        todaysBookings.clear();

        for (Booking booking:bookings) {
            for (BookingDetails bookingDetails: booking.getBookingDetails()) {
                if (Helper.stringToDate(bookingDetails.getDate()).equals(date)) {
                    /* TODO Needs fixing */
                    todaysBookings.add(booking);
                }
            }
        }
        return todaysBookings;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.previous_dayIv) {
            mMaterialCalendarView.setDateSelected(dateToday, false);
            dateToday = Helper.incrementDateByDays(dateToday, -1);
            setDate(dateToday);
        } else if (v.getId() == R.id.next_dayIv) {
            mMaterialCalendarView.setDateSelected(dateToday, false);
            dateToday = Helper.incrementDateByDays(dateToday, 1);
            setDate(dateToday);
        }

    }
}