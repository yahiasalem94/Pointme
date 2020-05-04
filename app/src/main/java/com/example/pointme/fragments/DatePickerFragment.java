//package com.example.pointme.fragments;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import androidx.core.content.ContextCompat;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.DatePicker;
//
//import com.example.pointme.constants.Type;
//import com.example.pointme.decorator.AllDaysDisabledDecorator;
//import com.example.pointme.interfaces.DatePickerDBInt;
//import com.example.pointme.R;
//import com.example.pointme.models.Appointment;
//import com.example.pointme.decorator.DayEnableDecorator;
//import com.example.pointme.models.Event;
//import com.example.pointme.models.Meeting;
//import com.example.pointme.models.ServiceProvider;
//import com.example.pointme.utils.Helper;
//import com.prolificinteractive.materialcalendarview.CalendarDay;
//import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
//import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
//import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//
//import static com.example.pointme.activities.MainActivity.APPOINTMENT;
//import static com.example.pointme.activities.MainActivity.EVENT;
//import static com.example.pointme.activities.MainActivity.TYPE;
//import static com.facebook.FacebookSdk.getApplicationContext;
//
//public class DatePickerFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
//
//    private String TAG = DatePickerFragment.class.getSimpleName();
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private HashMap<Integer, ArrayList<String>> mScheduleDBMap;
//    private HashMap<String, ArrayList<String>> mScheduleWBMap;
//    private ArrayList<String> mAppSchedule;
//    private ArrayList<String> mScheduleWB;
//    private ArrayList<String> mPickedList;
//    private String mPickedDate;
//    private String mDuration;
//    private Date startDate;
//    private int mMinPeriod;
//    private Animation slideUp;
//    private Animation slideDown;
//
//    private Button mCalendarButton;
//    private MaterialCalendarView mMaterialCalendarView;
//
//    private ServiceProvider mProfileInfo;
//    private Event mEvent;
//    private Appointment mAppointment;
//    private Meeting meeting;
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mProfileInfo = (ServiceProvider) getArguments().getSerializable("ProfileInfo");
//            int type = getArguments().getInt(TYPE);
////            if (type == Type.EVENT){
//                meeting = getArguments().getParcelable(EVENT);
//////                mDuration = mEvent.getDur();
//////                mMinPeriod = mEvent.getMinPeriod();
////            }else{
//                meeting = getArguments().getParcelable(APPOINTMENT);
////            }
//            setDates();
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//        Log.d(TAG, "DatePickerFragment");
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View mRootView = inflater.inflate(R.layout.fragment_date_picker, container, false);
//
//        mCalendarButton = mRootView.findViewById(R.id.calendar_button);
//        mCalendarButton.setVisibility(View.GONE);
//
//        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
//        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//
//        mMaterialCalendarView = mRootView.findViewById(R.id.calendarView);
//        mMaterialCalendarView.setAllowClickDaysOutsideCurrentMonth(false);
////        mMaterialCalendarView.setCurrentDate(enabledDates.get(0), true);
//
//        mMaterialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
//            @Override
//            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
//                materialCalendarView.removeDecorators();
//                String yearMonth = Helper.dateToString(calendarDay.getYear(), calendarDay.getMonth() + 1, 0);
////                DBCom.getAppBookingSlots(mDatePickerDBInt, mProfileInfo.getKey(), yearMonth, mAppSchedule, mScheduleWB, mDuration, mProfileInfo.getTimeD());
//                CalendarDay day = materialCalendarView.getSelectedDate();
//                if (mCalendarButton.getVisibility() == View.VISIBLE) {
//                    mCalendarButton.startAnimation(slideDown);
//                    mCalendarButton.setVisibility(View.GONE);
//                }
//                if(day != null && day.getMonth() == calendarDay.getMonth() && mCalendarButton.getVisibility() == View.GONE){
//                    mCalendarButton.startAnimation(slideUp);
//                    mCalendarButton.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        mMaterialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
//
//                mPickedDate = Helper.dateToString(calendarDay.getYear(), calendarDay.getMonth() + 1, 0);
//                if(mScheduleDBMap.containsKey(calendarDay.getDay())){
//                    mPickedList = mScheduleDBMap.get(calendarDay.getDay());
//                }else{
//                    String weekDay = Helper.getWeekDay(calendarDay);
//                    mPickedList = mScheduleWBMap.get(weekDay);
//                }
//
//                Bundle bundle = new Bundle();
//                bundle.putString("PickedDate", mPickedDate);
//                bundle.putStringArrayList("PickedList", mPickedList);
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
//                TimePickerFragment fragment = new TimePickerFragment();
//                fragment.setArguments(bundle);
//                transaction.replace(R.id.container, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//
//            }
//        });
//
//        mCalendarButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("PickedDate", mPickedDate);
//                bundle.putStringArrayList("PickedList", mPickedList);
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
//                TimePickerFragment fragment = new TimePickerFragment();
//                fragment.setArguments(bundle);
//                transaction.replace(R.id.container, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
//        return mRootView;
//    }
//
////    private void setDates() {
////        startDate = new Date();
////        mMinPeriod = meeting.getMinPeriod();
////
////        mDuration = meeting.getDur();
////    }
////
////    private Date stringToDate(String stringDate) {
////        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
////        Date date = null;
////
////        try {
////            date = formatter.parse(stringDate);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////        return date;
////    }
////
////    private Date getDur() {
////        String duration = meeting.getTimes().
////    }
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//       /* if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }*/
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//    }
//
//    @Override
//    public void setAppScheduleAndScheduleWB(ArrayList<String> appSchedule, ArrayList<String> scheduleWB) {
//        mAppSchedule = appSchedule;
//        mScheduleWB = scheduleWB;
//        CalendarDay day = CalendarDay.today();
//        String yearMonth = Helper.dateToString(day.getYear(), day.getMonth() + 1, 0);
////        DBCom.getAppBookingSlots(mDatePickerDBInt, mProfileInfo.getKey(), yearMonth, mAppSchedule, mScheduleWB, mDuration, mProfileInfo.getTimeD());
//    }
//
//    @Override
//    public void setAppBookingSlots(HashMap<Integer, ArrayList<String>> scheduleDBMap, HashMap<String, ArrayList<String>> scheduleWBMap) {
//        mScheduleDBMap = scheduleDBMap;
//        mScheduleWBMap = scheduleWBMap;
//
//        mMaterialCalendarView.addDecorator(new AllDaysDisabledDecorator());
//        mMaterialCalendarView.addDecorator(new DayEnableDecorator(scheduleDBMap, scheduleWBMap, mMinPeriod, ContextCompat.getDrawable(getActivity(), R.drawable.circle_border)));
//    }
//}
