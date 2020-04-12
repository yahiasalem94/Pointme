package com.example.pointme.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.pointme.interfaces.CheckBookerFreeDBInt;
import com.example.pointme.R;
import com.example.pointme.adapters.TimeAdapter;


import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class TimePickerFragment extends Fragment implements TimeAdapter.OnItemClicked, CheckBookerFreeDBInt {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String TAG = "TimePickerFragment";
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

    private GestureDetectorCompat mDetector;

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
        return inflater.inflate(R.layout.fragment_time_picker, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        mTimeRecView = view.findViewById(R.id.time_rec_view);
        mTimeButton = view.findViewById(R.id.time_button);

        mCheckBookerFreeDBInt = this;

        mPickedDate = getArguments().getString("PickedDate");
        mPickedList = getArguments().getStringArrayList("PickedList");

        mAdapter = new TimeAdapter(mPickedList, getActivity());
        mAdapter.setOnClick(this);
        mTimeRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTimeRecView.setAdapter(mAdapter);

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DBCom.checkBookerFree(mCheckBookerFreeDBInt, mDateTime);
                MyBookingFragment fragment = new MyBookingFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        Log.i(TAG, "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i(TAG, "Right to Left");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i(TAG, "Left to Right");
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        mTimeRecView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);

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
      //  mListener = null;
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
}
