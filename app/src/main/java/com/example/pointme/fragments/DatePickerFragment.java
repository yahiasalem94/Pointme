package com.example.pointme.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.pointme.R;
import com.example.pointme.classes.AllDaysDisabledDecorator;
import com.example.pointme.classes.DayEnableDecorator;
import com.example.pointme.classes.PointmeDate;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.DayOfWeek;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DatePickerFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<CalendarDay> enabledDates;
    private ArrayList<String> dates;
    private ArrayList<String> pickedDates;
    private  String pickedDate;
    private ArrayList<String> dayDates;
    private Animation slideUp;
    private Animation slideDown;

    private Button mCalendarButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        enabledDates = new ArrayList<>();
        pickedDates = new ArrayList<>();
        String[] array = {"Su12301330", "Th110013001500170018002000"};
        //dayDates = new ArrayList<>(Arrays.asList(array));
        dates = getArguments().getStringArrayList("Dates");

        /*for (String date: dates){
            PointmeDate pointmeDate = PointmeDate.StringToDate(date);
            CalendarDay day = CalendarDay.from(pointmeDate.getYear(), pointmeDate.getMonth(), pointmeDate.getDay());
            if(!day.isBefore(CalendarDay.today())){
                enabledDates.add(day);
            }
        }*/

        mCalendarButton = (Button) view.findViewById(R.id.calendar_button);
        mCalendarButton.setVisibility(View.GONE);

        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        MaterialCalendarView materialCalendarView=(MaterialCalendarView) view.findViewById (R.id.calendarView);
        materialCalendarView.setAllowClickDaysOutsideCurrentMonth(false);
        //materialCalendarView.setCurrentDate(enabledDates.get(0), true);

        materialCalendarView.addDecorator(new AllDaysDisabledDecorator());
        materialCalendarView.addDecorator(new DayEnableDecorator(dates, 8));

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                materialCalendarView.removeDecorators();
                materialCalendarView.addDecorator(new AllDaysDisabledDecorator());
                materialCalendarView.addDecorator(new DayEnableDecorator(dates, 8));
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
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                if(mCalendarButton.getVisibility() == View.GONE){
                    mCalendarButton.startAnimation(slideUp);
                    mCalendarButton.setVisibility(View.VISIBLE);
                }
                pickedDates = new ArrayList<>();
                char c = dates.get(0).charAt(0);
                String aday = "";
                if (c >= 'F' && c <= 'W'){
                    DayOfWeek dayOfWeek = calendarDay.getDate().getDayOfWeek();
                    switch (dayOfWeek) {
                        case FRIDAY:
                            aday = "Fr";
                            break;
                        case SATURDAY:
                            aday = "Sa";
                            break;
                        case SUNDAY:
                            aday = "Su";
                            break;
                        case MONDAY:
                            aday = "Mo";
                            break;
                        case TUESDAY:
                            aday = "Tu";
                            break;
                        case WEDNESDAY:
                            aday = "We";
                            break;
                        case THURSDAY:
                            aday = "Th";
                            break;
                        default:
                            break;
                    }
                    for (String s : dates) {
                        if (s.contains(aday)) {
                            pickedDate = s;
                            break;
                        }
                    }
                }
                String date = calendarDayToString(calendarDay);
                Log.d("Ramy", date);
                for(String dateTime: dates){
                    if(dateTime.startsWith(date)){
                        pickedDates.add(dateTime);
                    }
                }
            }
        });

        mCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Dates", pickedDates);
                bundle.putString("Date", pickedDate);
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

    public static String calendarDayToString(CalendarDay day){
        String string = "", temp;
        temp = String.valueOf(day.getYear());
        string+=temp;
        temp = String.valueOf(day.getMonth());
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        string+=temp;
        temp = String.valueOf(day.getDay());
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        string+=temp;
        return string;
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
