package com.example.pointme.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.pointme.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NumberPickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumberPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumberPickerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STATE = "state";

    // TODO: Rename and change types of parameters
    private int state;

    private OnFragmentInteractionListener mListener;

    private NumberPicker mHours;
    private NumberPicker mMinutes;

    private String [] mHoursArray = {"00", "01", "02", "03"};
    private String [] mMinutesArray = {"00", "15", "30", "45"};

    public NumberPickerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NumberPickerFragment newInstance(int state) {
        NumberPickerFragment fragment = new NumberPickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            state = getArguments().getInt(ARG_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_number_picker, container, false);
        mHours = (NumberPicker) view.findViewById(R.id.np_hours);
        mMinutes = (NumberPicker) view.findViewById(R.id.np_minutes);
        mHours.setMinValue(0);
        mHours.setMaxValue(3);
        mHours.setDisplayedValues(mHoursArray);
        mHours.setWrapSelectorWheel(true);
        mMinutes.setMinValue(0);
        mMinutes.setMaxValue(3);
        mMinutes.setDisplayedValues(mMinutesArray);
        mMinutes.setWrapSelectorWheel(true);
        ((TimeBottomSheetFragment)getParentFragment()).setmStartHour(mHoursArray[mHours.getValue()]);
        ((TimeBottomSheetFragment)getParentFragment()).setmEndHour(mHoursArray[mHours.getValue()]);
        ((TimeBottomSheetFragment)getParentFragment()).setmStartMin(mMinutesArray[mMinutes.getValue()]);
        ((TimeBottomSheetFragment)getParentFragment()).setmEndMin(mMinutesArray[mMinutes.getValue()]);
        mHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(state==1){
                    ((TimeBottomSheetFragment)getParentFragment()).setmStartHour(mHoursArray[newVal]);
                }else if(state==2){
                    ((TimeBottomSheetFragment)getParentFragment()).setmEndHour(mHoursArray[newVal]);
                }
            }
        });
        mMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(state==1){
                    ((TimeBottomSheetFragment)getParentFragment()).setmStartMin(mMinutesArray[newVal]);
                }else if(state==2){
                    ((TimeBottomSheetFragment)getParentFragment()).setmEndMin(mMinutesArray[newVal]);
                }
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
