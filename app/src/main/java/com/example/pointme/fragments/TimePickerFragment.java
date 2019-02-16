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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.pointme.R;
import com.example.pointme.adapters.TimeAdapter;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimePickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends Fragment implements TimeAdapter.OnItemClicked {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private String[] array = { "JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY",
     //       "AUG", "SEPT", "OCT", "NOV", "DEC" , "ramy", "ramy1", "ramy2", "ramy3", "ramy4", "ramy5"};
    private ArrayList<String> dates;
    private ArrayList<String> times;
    private String time;
    private String date;

    private RecyclerView mTimeRecView;
    private ArrayAdapter mArrayAdapter;
    private TimeAdapter adapter;
    private Button mTimeButton;

    private Animation slideUp;

    private OnFragmentInteractionListener mListener;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        times = new ArrayList<>();
        dates = getArguments().getStringArrayList("Dates");
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
        }

        adapter = new TimeAdapter(times, getContext());
        adapter.setOnClick(this);
        mTimeRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTimeRecView.setAdapter(adapter);

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datetime = "";
                for(String date: dates){
                    if(date.contains(time)){
                        datetime = date;
                        break;
                    }
                }
                Log.d("Ramy", datetime);
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

        time = viewHolder.mTimeTextView.getText().toString().replace(":","");

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