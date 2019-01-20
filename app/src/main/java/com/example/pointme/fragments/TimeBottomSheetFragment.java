package com.example.pointme.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pointme.R;
import com.example.pointme.classes.TimePickerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimeBottomSheetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimeBottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeBottomSheetFragment extends BottomSheetDialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ViewPager mTimes;
    private TabLayout mTabs;
    private TimePickerAdapter mAdapter;
    private Button mButton;

    private String mStartHour;
    private String mStartMin;
    private String mEndHour;
    private String mEndMin;

    //declare interface
    private OnTimeSelected onClick;

    //make interface like this
    public interface OnTimeSelected {
        void onTimeSelected(String StartHour, String StartMin, String EndHour, String EndMin);
    }

    public TimeBottomSheetFragment() {
        // Required empty public constructor
    }
    public static TimeBottomSheetFragment newInstance(String param1, String param2) {
        TimeBottomSheetFragment fragment = new TimeBottomSheetFragment();
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
        View contentView = View.inflate(getContext(), R.layout.fragment_time_bottom_sheet, null);
        View view = inflater.inflate(R.layout.fragment_time_bottom_sheet, container, false);
        mTimes = (ViewPager) view.findViewById(R.id.vp_times);
        mTabs = (TabLayout) view.findViewById(R.id.tabs);
        mButton = (Button) view.findViewById(R.id.btn_SetTimeRange);
        mAdapter = new TimePickerAdapter(getChildFragmentManager());
        mTimes.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mTimes);
        mTabs.getTabAt(0).setText("Start Time");
        mTabs.getTabAt(1).setText("End Time");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((mStartHour+mStartMin).compareTo(mEndHour+mEndMin)>=0){
                    Toast.makeText(getActivity(), "End time must be greater than Start time.", Toast.LENGTH_LONG).show();
                }else {
                    onClick.onTimeSelected(mStartHour, mStartMin, mEndHour, mEndMin);
                    dismiss();
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
      /*  if (context instanceof OnFragmentInteractionListener) {
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

    public void setOnTimeSelectedListener(OnTimeSelected onClick)
    {
        this.onClick=onClick;
    }

    public void setmStartHour(String mStartHour) {
        this.mStartHour = mStartHour;
    }

    public void setmStartMin(String mStartMin) {
        this.mStartMin = mStartMin;
    }

    public void setmEndHour(String mEndHour) {
        this.mEndHour = mEndHour;
    }

    public void setmEndMin(String mEndMin) {
        this.mEndMin = mEndMin;
    }

}
