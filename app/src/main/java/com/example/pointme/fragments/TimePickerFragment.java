package com.example.pointme.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.pointme.interfaces.CheckBookerFreeDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.TimeAdapter;
import com.example.pointme.backendCommunications.DBCom;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class TimePickerFragment extends Fragment implements TimeAdapter.OnItemClicked, CheckBookerFreeDBInt {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> dates;
    private ArrayList<String> mPickedList;
    private String mPickedDate;
    private String mDateTime;
    private String time;
    private String date;

    private RecyclerView mTimeRecView;
    private ArrayAdapter mArrayAdapter;
    private TimeAdapter mAdapter;
    private Button mTimeButton;
    private CheckBookerFreeDBInt mCheckBookerFreeDBInt;

    private Animation slideUp;

    private OnFragmentInteractionListener mListener;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(String param1, String param2) {
        TimePickerFragment fragment = new TimePickerFragment();
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
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        mTimeRecView = (RecyclerView) view.findViewById(R.id.time_rec_view);
        mTimeButton = (Button) view.findViewById(R.id.time_button);

        mCheckBookerFreeDBInt = this;

        mPickedDate = getArguments().getString("PickedDate");
        mPickedList = getArguments().getStringArrayList("PickedList");
        /*dates = getArguments().getStringArrayList("Dates");
        if(!dates.isEmpty()) {
            for (String date : dates) {
                String hours = date.substring(8, 10);
                String minutes = date.substring(10);
                times.add(hours + ":" + minutes);
            }
        }else{
            date = getArguments().getString("Date");
            for(int i = 2; i + 8 <= date.length(); i = i + 8){
                String stHours = date.substring(i, i+2);
                String stMinutes = date.substring(i+2, i+4);
                String enHours = date.substring(i+4, i+6);
                String enMinutes = date.substring(i+6, i+8);
                times.add(stHours + ":" + stMinutes + " - " + enHours + ":" + enMinutes);
            }
        }*/

        mAdapter = new TimeAdapter(mPickedList, getContext());
        mAdapter.setOnClick(this);
        mTimeRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTimeRecView.setAdapter(mAdapter);

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBCom.checkBookerFree(mCheckBookerFreeDBInt, mDateTime);
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
        /*if (context instanceof OnFragmentInteractionListener) {
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
    public void onItemClick(TimeAdapter.Holder viewHolder, int position) {
        for(TimeAdapter.Holder holder : TimeAdapter.holders) {
            if (holder.equals(viewHolder)) {
                holder.mTimeTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                holder.mCheckImageView.setVisibility(View.VISIBLE);
                TimeAdapter.selPos = position;
                TimeAdapter.isSelected = true;
                mDateTime = mPickedDate + mPickedList.get(position);
            } else {
                holder.mCheckImageView.setVisibility(View.GONE);
                if (holder.mTimeTextView.getCurrentTextColor() == Color.WHITE){
                    ObjectAnimator colorAnim = ObjectAnimator.ofObject(holder.mTimeTextView, "textColor", new ArgbEvaluator(),
                            Color.WHITE, Color.GRAY);
                    colorAnim.setDuration(300);
                    colorAnim.start();
                }
            }

        }
        if(mTimeButton.getVisibility() == View.GONE) {
            mTimeButton.startAnimation(slideUp);
            mTimeButton.setVisibility(View.VISIBLE);
        }

        //time = viewHolder.mTimeTextView.getText().toString().replace(":","");

    }

    @Override
    public void isBookerFree(boolean free) {

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
