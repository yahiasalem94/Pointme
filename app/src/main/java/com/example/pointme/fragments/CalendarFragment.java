package com.example.pointme.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointme.R;
import com.example.pointme.activities.LoginActivity;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.activities.SignupActivity;
import com.example.pointme.adapters.TimeAdapter;
import com.example.pointme.constants.Type;
import com.example.pointme.decorator.AllDaysDisabledDecorator;
import com.example.pointme.decorator.DayEnableDecorator;
import com.example.pointme.decorator.GridSpacingItemDecorator;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.Meeting;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.Helper;
import com.example.pointme.viewModels.BookingSlotsViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.JsonObject;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.pointme.activities.MainActivity.APPOINTMENT;
import static com.example.pointme.activities.MainActivity.CONFIRM_DATE;
import static com.example.pointme.activities.MainActivity.CONFIRM_TIME;
import static com.example.pointme.activities.MainActivity.EVENT;
import static com.example.pointme.activities.MainActivity.MEETING;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.example.pointme.activities.MainActivity.TYPE;
import static com.example.pointme.constants.Constants.GET_BOOKING_SLOTS;

public class CalendarFragment extends Fragment implements TimeAdapter.TimeAdapterOnClickHandler {

    private static final String TAG = CalendarFragment.class.getSimpleName();
    private ServiceProvider profileInfo;
    private Event event;
    private Appointment appointment;
    private @Type int type;


    private String meetingId;
    private String meetingName;
    private int minPeriod;
    private String startDate;
    private String endDate;
    private HashMap<String, String> times;
    private ArrayList<String> days;
    private String mPickedDate;
    private ArrayList<String> mTimePickedList = new ArrayList<>();

    /* Appointment */
    private String timeDiff;
    /*  Events */
    private String schedueType;
    private ArrayList<String> stringDates;
    private ArrayList<LocalDate> dates = new ArrayList<>();
    private CalendarDay minDate;

    /* Views */
    private MaterialCalendarView mMaterialCalendarView;
    private RecyclerView timesRecyclerView;
    private TextView mNoDateTv;
    private ProgressDialog progressDialog;
    private TimeAdapter mAdapter;

    /* Firebase Functions */
    private FirebaseFunctions mFunctions;

    private BookingSlotsViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileInfo = getArguments().getParcelable(PROFILE_INFO);
            type = getArguments().getInt(TYPE);
            if (type == Type.EVENT) {
                event = getArguments().getParcelable(MEETING);
                meetingId = event.getMeetingID();
                meetingName = event.getName();
                minPeriod = event.getMinPeriod();
                startDate = event.getStartDate();
                endDate = event.getEndDate();
                schedueType = event.getScheduleType();
                stringDates = event.getDates();
                times = (HashMap<String, String>) event.getTimes();
            } else {
                appointment = getArguments().getParcelable(MEETING);
                meetingId = appointment.getMeetingID();
                meetingName = appointment.getName();
                minPeriod = appointment.getMinPeriod();
                startDate = appointment.getStartDate();
                endDate = appointment.getEndDate();
                schedueType = "WB";
                times = (HashMap<String, String>) appointment.getTimes();
                timeDiff = appointment.getTimeDiff();
            }
            days = new ArrayList<>(times.keySet());
        }
        mAdapter = new TimeAdapter(getActivity(), this);
        mViewModel = new ViewModelProvider(this).get(BookingSlotsViewModel.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_date_picker, container, false);
        timesRecyclerView = mRootView.findViewById(R.id.times_recycler_list);
        timesRecyclerView.setNestedScrollingEnabled(false);

        mNoDateTv = mRootView.findViewById(R.id.noDatesSelected);

        setupRecyclerView();

        mMaterialCalendarView = mRootView.findViewById(R.id.calendarView);
        mMaterialCalendarView.addDecorator(new AllDaysDisabledDecorator());
        handleAppointments();

        mMaterialCalendarView.setOnDateChangedListener((widget, calendarDay, selected) -> {
            progressDialog = ProgressDialog.show(getActivity(),
                    "ProgressDialog", "Fetching Booking Slots");
            mTimePickedList.clear();
            mPickedDate = Helper.dateToString(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay());
            if (type == Type.EVENT) {
                if (schedueType.equals("WB")) {
                    mPickedDate = Helper.getWeekDay(calendarDay);
                    mTimePickedList.add(times.get(mPickedDate));
                    mAdapter.setTimesData(mTimePickedList);
                    showRecyclerView();
                } else {
                    mTimePickedList.add(times.get(Helper.dateToString(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay())));
                    mAdapter.setTimesData(mTimePickedList);
                    showRecyclerView();
                }
            } else {
                Map<String, String> data = new HashMap<>();
                data.put("date", mPickedDate);
                data.put("spID", appointment.getSpID());
                data.put("appSchedule", times.get(Helper.getWeekDay(calendarDay)));
                data.put("timeDiff", timeDiff);
                data.put("duration", appointment.getDuration());

                mViewModel.getBookingSlots(data);
            }
        });

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addBookingSlotsObserver();
    }

    private void addBookingSlotsObserver() {
        mViewModel.bookingSlots.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> bookingSlots) {
                if (bookingSlots != null) {
//                    mTimePickedList = (ArrayList) bookingSlots.getAsJsonObject("bookingSlot").getx

                    mAdapter.setTimesData(bookingSlots);
                    showRecyclerView();
                }
            }
        });
    }

    private void setupRecyclerView() {
        // Create the grid layout manager with 2 columns.
        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(getActivity());
        timesRecyclerView.setLayoutManager(linearlayoutManager);
        // Set data adapter.
        timesRecyclerView.setAdapter(mAdapter);
    }

    private void showRecyclerView() {
        mNoDateTv.setVisibility(View.GONE);
        progressDialog.dismiss();
        timesRecyclerView.setVisibility(View.VISIBLE);
    }
    private void handleAppointments() {

        if (schedueType.equals("WB")) {
            mMaterialCalendarView.addDecorator(new DayEnableDecorator(days, minPeriod, startDate, endDate));
        } else {
            for (int i = 0; i < stringDates.size(); i++) {
                CalendarDay date = Helper.stringToDate(stringDates.get(i));
                dates.add(date.getDate());
            }
            mMaterialCalendarView.addDecorator(new DayEnableDecorator(dates, minPeriod, startDate, endDate));
        }

    }

    @Override
    public void onClick(int position) {
        /* Time Adapter Click */
        BookingConfirmationFragment fragment = new BookingConfirmationFragment();

        Bundle bundle = new Bundle();
        bundle.putString(CONFIRM_DATE, mPickedDate);
        bundle.putString(CONFIRM_TIME, mTimePickedList.get(position));
        bundle.putString("MeetingId", meetingId);
        bundle.putString("MeetingName", meetingName);
        bundle.putInt(TYPE, type);
        bundle.putParcelable(PROFILE_INFO, profileInfo);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
