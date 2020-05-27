package com.example.pointme.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointme.R;
import com.example.pointme.adapters.TimeAdapter;
import com.example.pointme.constants.Constants;
import com.example.pointme.decorator.AllDaysDisabledDecorator;
import com.example.pointme.decorator.DayEnableDecorator;
import com.example.pointme.models.Appointment;
import com.example.pointme.models.Event;
import com.example.pointme.models.Meeting;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.utils.Helper;
import com.example.pointme.viewModels.BookingSlotsViewModel;
import com.google.firebase.functions.FirebaseFunctions;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pointme.activities.MainActivity.CONFIRM_DATE;
import static com.example.pointme.activities.MainActivity.CONFIRM_TIME;
import static com.example.pointme.activities.MainActivity.MEETING;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.example.pointme.activities.MainActivity.TYPE;
import static com.example.pointme.constants.Constants.WEEKLY_BASED;

public class CalendarFragment extends Fragment implements TimeAdapter.TimeAdapterOnClickHandler {

    private static final String TAG = CalendarFragment.class.getSimpleName();
    private ServiceProvider profileInfo;
    private Meeting meeting;
    private @Constants.Type int type;

    private boolean isEvent;
    private boolean isAppointment;

    Map<String, String> bookingSlotsData = new HashMap<>();

    private String meetingId;
    private String meetingName;
    private String spId;
    private int minPeriod;
    private String startDate;
    private String endDate;
    private HashMap<String, String> times;
    private ArrayList<String> days;
    private String mPickedDate;
    private ArrayList<String> mTimePickedList = new ArrayList<>();

    /* Appointment */
    private String timeDiff;
    private String duration;
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
            meeting = getArguments().getParcelable(MEETING);
            type = getArguments().getInt(TYPE);

            meetingId = meeting.getMeetingID();
            meetingName = meeting.getName();
            spId = meeting.getSpID();
            minPeriod = meeting.getMinPeriod();
            startDate = meeting.getStartDate();
            endDate = meeting.getEndDate();


            if (meeting instanceof Event) {
                isEvent = true;
                schedueType = ((Event) meeting).getScheduleType();
                stringDates = ((Event) meeting).getDates();
                times = ((Event) meeting).getTimes();
                days = new ArrayList<>(times.keySet());
            } else if (meeting instanceof Appointment) {
                isAppointment = true;
                times = ((Appointment) meeting).getTimes();
                timeDiff = ((Appointment) meeting).getTimeDiff();
                duration = ((Appointment) meeting).getDuration();

                days = new ArrayList<>(times.keySet());
            }


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
            if (isEvent) {
                if (schedueType.equals(WEEKLY_BASED)) {
                    mPickedDate = Helper.getWeekDay(calendarDay);
                    mTimePickedList.add(times.get(mPickedDate));
                    mAdapter.setTimesData(mTimePickedList);
                    showRecyclerView();
                } else {
                    mTimePickedList.add(times.get(Helper.dateToString(calendarDay.getYear(), calendarDay.getMonth(), calendarDay.getDay())));
                    mAdapter.setTimesData(mTimePickedList);
                    showRecyclerView();
                }
            } else if (isAppointment) {
                bookingSlotsData.clear();
                bookingSlotsData.put("date", mPickedDate);
                bookingSlotsData.put("spID", spId);
                bookingSlotsData.put("appSchedule", times.get(Helper.getWeekDay(calendarDay)));
                bookingSlotsData.put("timeDiff", timeDiff);
                bookingSlotsData.put("duration", duration);

                mViewModel.getBookingSlots(bookingSlotsData);
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
                    mTimePickedList.clear();
                    mTimePickedList.addAll(bookingSlots);
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

        if (isAppointment || schedueType.equals(WEEKLY_BASED)) {
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
