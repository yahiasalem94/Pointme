package com.example.pointme.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.R;
import com.example.pointme.constants.Type;
import com.example.pointme.models.Booking;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.viewModels.AddBookingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.pointme.activities.MainActivity.CONFIRM_DATE;
import static com.example.pointme.activities.MainActivity.CONFIRM_TIME;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.example.pointme.activities.MainActivity.TYPE;

public class BookingConfirmationFragment extends Fragment implements View.OnClickListener {

    private String mDate;
    private String mTime;
    private String meetingId;
    private String meetingName;
    private @Type  int type;
    private ServiceProvider profileInfo;

    private FirebaseAuth mAuth;
    private AddBookingViewModel mAddBookingViewModel;
    private ProgressDialog progressDialog;
    /* Views */
    private TextView mConfirmDate;
    private TextView mConfirmTime;
    private MaterialButton confirmButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Booking Confirmation");
        if (getArguments() != null) {
            mDate = getArguments().getString(CONFIRM_DATE);
            mTime = getArguments().getString(CONFIRM_TIME);
            meetingId = getArguments().getString("MeetingId");
            meetingName = getArguments().getString("MeetingName");
            profileInfo = getArguments().getParcelable(PROFILE_INFO);
            type = getArguments().getInt(TYPE);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_booking_confirmation, container, false);
        mConfirmDate = mRootView.findViewById(R.id.confirm_booking_date);
        mConfirmTime = mRootView.findViewById(R.id.confirm_booking_time);
        confirmButton = mRootView.findViewById(R.id.confirm_button);

        mConfirmDate.setText(mDate);
        mConfirmTime.setText(mTime);

        confirmButton.setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mAddBookingViewModel = new ViewModelProvider(BookingConfirmationFragment.this).get(AddBookingViewModel.class);
        addObserver();
    }

    private void addObserver() {
        mAddBookingViewModel.bookingAdded.observe(getViewLifecycleOwner(), isSuccessful -> {
            progressDialog.dismiss();
            if (isSuccessful) {
                Toast.makeText(getActivity(), "Booking Sumbitted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error review not submitted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        progressDialog = ProgressDialog.show(getActivity(),
                "ProgressDialog", "Confirming Booking");
        Booking booking = new Booking();
        booking.setCrID(mAuth.getCurrentUser().getUid());
        booking.setSpName(profileInfo.getName());
        booking.setSpID(profileInfo.getuID());
        booking.setMeetingID(meetingId);
        booking.setMeetingName(meetingName);
        /* TODO need to handle approval */
        booking.setApprovalStatus("Approved");
        booking.setCancelled(false);

        if (type == Type.EVENT) {
            booking.setType("Event");
        } else {
            booking.setType("Appointment");
        }

        /* TODO Handle number of sessions */

        mAddBookingViewModel.addBooking(booking);
    }
}
