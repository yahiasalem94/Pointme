package com.example.pointme.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.R;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.classes.DayAdapter;
import com.example.pointme.classes.Event;
import com.example.pointme.classes.ScheduleHelper;
import com.example.pointme.classes.TimeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DayPickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DayPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayPickerFragment extends Fragment implements DayAdapter.OnDayClicked, TimeBottomSheetFragment.OnTimeSelected{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mPosition;
    private ArrayList<String> mDays;
    private String mDay;

    private RecyclerView mDayRecView;
    private DayAdapter mAdapter;
    private DayAdapter.DayHolder mHolder;
    private DayPickerFragment fg;
    private Button mButton;
    private Bundle mBundle;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private OnFragmentInteractionListener mListener;
    private  TimeBottomSheetFragment fragment;

    public DayPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DayPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayPickerFragment newInstance(String param1, String param2) {
        DayPickerFragment fragment = new DayPickerFragment();
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
        View view = inflater.inflate(R.layout.fragment_day_picker, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDayRecView = (RecyclerView) view.findViewById(R.id.day_rec_view);
        mButton = (Button) view.findViewById(R.id.btn_save_event);
        mBundle = getArguments();
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayList<String> dayslist = new ArrayList<>(Arrays.asList(days));
        mDays = new ArrayList<>();
        mAdapter = new DayAdapter(dayslist, getContext());
        mAdapter.setOnClick(this);
        fg = this;
        mDayRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDayRecView.setAdapter(mAdapter);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDays.isEmpty()){
                    Toast.makeText(getContext(), "Please choose the event timings.", Toast.LENGTH_LONG).show();
                }else {
                    Event event = new Event(mBundle.getString("EventName"), mBundle.getString("EventDescription"), "No loc",
                            mBundle.getInt("MaxNum"), 0, mBundle.getInt("Fees"), 0, true, true, false, mDays);
                    ScheduleHelper.uploadEvent(mAuth, mDatabase, event);
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

    @Override
    public void onDayClick(DayAdapter.DayHolder viewHolder, int position) {
        if(!viewHolder.isFlag()){
            viewHolder.setFlag(true);
            mAdapter.notifyItemChanged(position, viewHolder);
            /*viewHolder.mLayout.clearAnimation();
            viewHolder.mLayout.startAnimation(mSlideDown);
            viewHolder.mLayout.setVisibility(View.VISIBLE);*/
            mHolder = viewHolder;
            mPosition = position;
            viewHolder.mTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment = new TimeBottomSheetFragment();
                    fragment.setOnTimeSelectedListener(fg);
                    fragment.show(getActivity().getSupportFragmentManager(), "TAG");
                }
            });
        }else{
            viewHolder.setFlag(false);
            mAdapter.notifyItemChanged(position, viewHolder);
            /*viewHolder.mLayout.clearAnimation();
            viewHolder.mLayout.startAnimation(mSlideUp);
            viewHolder.mLayout.setVisibility(View.GONE);*/
        }
    }

    @Override
    public void onTimeSelected(String StartHour, String StartMin, String EndHour, String EndMin) {
        mAdapter.setTVVisible(StartHour + ":" + StartMin + " - " + EndHour + ":" + EndMin , mHolder);
        mAdapter.notifyItemChanged(mPosition, mHolder);
        mDay = mHolder.mDayTextView.getText().toString().substring(0,2);
        for(String s : mDays){
            if(s.contains(mDay)){
                mDay = s;
                mDays.remove(s);
                break;
            }
        }
        mDay+=StartHour+StartMin+EndHour+EndMin;
        mDays.add(mDay);
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
