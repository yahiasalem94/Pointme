package com.example.pointme.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.pointme.Interfaces.DatePickerDBInt;
import com.example.pointme.R;
import com.example.pointme.backend.DBCom;
import com.example.pointme.models.AllDaysDisabledDecorator;
import com.example.pointme.models.DayEnableDecorator;
import com.example.pointme.models.Helper;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DatePickerFragment extends Fragment implements DatePickerDBInt, DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private HashMap<Integer, ArrayList<String>> mScheduleDBMap;
    private HashMap<String, ArrayList<String>> mScheduleWBMap;
    private ArrayList<String> mAppSchedule;
    private ArrayList<String> mScheduleWB;
    private ArrayList<String> mPickedList;
    private String mPickedDate;
    private Animation slideUp;
    private Animation slideDown;

    private Button mCalendarButton;
    private MaterialCalendarView mMaterialCalendarView;

    private DatePickerDBInt mDatePickerDBInt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "DatePickerFragment";
    private OnFragmentInteractionListener mListener;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatePickerFragment newInstance(String param1, String param2) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d(TAG, "DatePickerFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);

        mDatePickerDBInt = this;

        mCalendarButton = (Button) view.findViewById(R.id.calendar_button);
        mCalendarButton.setVisibility(View.GONE);

        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        mMaterialCalendarView =(MaterialCalendarView) view.findViewById (R.id.calendarView);
        mMaterialCalendarView.setAllowClickDaysOutsideCurrentMonth(false);
        //mMaterialCalendarView.setCurrentDate(enabledDates.get(0), true);

        DBCom.getAppScheduleAndScheduleWB(mDatePickerDBInt, "uid");

        mMaterialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                materialCalendarView.removeDecorators();
                String yearMonth = Helper.dateToString(calendarDay.getYear(), calendarDay.getMonth() + 1, 0);
                DBCom.getAppBookingSlots(mDatePickerDBInt, "uid", yearMonth, mAppSchedule, mScheduleWB, "dur", "timeD");
                CalendarDay day = materialCalendarView.getSelectedDate();
                if (mCalendarButton.getVisibility() == View.VISIBLE) {
                    mCalendarButton.startAnimation(slideDown);
                    mCalendarButton.setVisibility(View.GONE);
                }
                if(day != null && day.getMonth() == calendarDay.getMonth() && mCalendarButton.getVisibility() == View.GONE){
                    mCalendarButton.startAnimation(slideUp);
                    mCalendarButton.setVisibility(View.VISIBLE);
                }
            }
        });
        mMaterialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                if(mCalendarButton.getVisibility() == View.GONE){
                    mCalendarButton.startAnimation(slideUp);
                    mCalendarButton.setVisibility(View.VISIBLE);
                }
                mPickedDate = Helper.dateToString(calendarDay.getYear(), calendarDay.getMonth() + 1, 0);
                if(mScheduleDBMap.containsKey(calendarDay.getDay())){
                    mPickedList = mScheduleDBMap.get(calendarDay.getDay());
                }else{
                    String weekDay = Helper.getWeekDay(calendarDay);
                    mPickedList = mScheduleWBMap.get(weekDay);
                }
            }
        });

        mCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("PickedDate", mPickedDate);
                bundle.putStringArrayList("PickedList", mPickedList);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
                TimePickerFragment fragment = new TimePickerFragment();
                fragment.setArguments(bundle);
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void setAppScheduleAndScheduleWB(ArrayList<String> appSchedule, ArrayList<String> scheduleWB) {
        mAppSchedule = appSchedule;
        mScheduleWB = scheduleWB;
        CalendarDay day = CalendarDay.today();
        String yearMonth = Helper.dateToString(day.getYear(), day.getMonth() + 1, 0);
        DBCom.getAppBookingSlots(mDatePickerDBInt, "uid", yearMonth, mAppSchedule, mScheduleWB, "dur", "timeD");
    }

    @Override
    public void setAppBookingSlots(HashMap<Integer, ArrayList<String>> scheduleDBMap, HashMap<String, ArrayList<String>> scheduleWBMap) {
        mScheduleDBMap = scheduleDBMap;
        mScheduleWBMap = scheduleWBMap;
        mMaterialCalendarView.addDecorator(new AllDaysDisabledDecorator());
        mMaterialCalendarView.addDecorator(new DayEnableDecorator(scheduleDBMap, scheduleWBMap, 8));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
