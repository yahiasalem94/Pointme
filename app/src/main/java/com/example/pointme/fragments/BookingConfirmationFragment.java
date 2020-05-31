package com.example.pointme.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.pointme.activities.GoogleMapsActivity;
import com.example.pointme.activities.MainActivity;
import com.example.pointme.constants.Constants;
import com.example.pointme.models.Booking;
import com.example.pointme.models.ServiceProvider;
import com.example.pointme.viewModels.AddBookingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.pointme.activities.GoogleMapsActivity.ADDRESS;
import static com.example.pointme.activities.MainActivity.CONFIRM_DATE;
import static com.example.pointme.activities.MainActivity.CONFIRM_TIME;
import static com.example.pointme.activities.MainActivity.PROFILE_INFO;
import static com.example.pointme.activities.MainActivity.TYPE;

public class BookingConfirmationFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_CODE = 444;
    private String address;
    private String mDate;
    private String mTime;
    private String meetingId;
    private String meetingName;
    private @Constants.Type int type;
    private ServiceProvider profileInfo;

    private FirebaseAuth mAuth;
    private AddBookingViewModel mAddBookingViewModel;
    private ProgressDialog progressDialog;
    /* Views */
    private TextView mConfirmDate;
    private TextView mConfirmTime;
    private TextView mAddress;
    private MaterialButton confirmButton;
    private MaterialButton mLocationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Booking Confirmation");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_booking_confirmation, container, false);
        mConfirmDate = mRootView.findViewById(R.id.confirm_booking_date);
        mConfirmTime = mRootView.findViewById(R.id.confirm_booking_time);
        mAddress = mRootView.findViewById(R.id.address_tv);
        confirmButton = mRootView.findViewById(R.id.confirm_button);
        mLocationButton = mRootView.findViewById(R.id.location_button);

        fixDateTimeView();
        mConfirmDate.setText(mDate);
        mConfirmTime.setText(mTime);

        mLocationButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);

        return mRootView;
    }

    private void fixDateTimeView() {

        String year = mDate.substring(0, 4);
        String month = mDate.substring(4, 6);
        String day = mTime.substring(6);
        mDate = day + "/" + month + "/" + year;

        String stHour = mTime.substring(0, 2);
        String stMin = mTime.substring(2, 4);
        String endHour = mTime.substring(4, 6);
        String endMin = mTime.substring(6, 8);
        mTime = stHour + ":" + stMin + " - " + endHour + ":" + endMin;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == REQUEST_CODE) {
            address = data.getStringExtra(ADDRESS);
            mLocationButton.setVisibility(View.GONE);
            mAddress.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            mAddress.setText(address);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.confirm_button) {
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

            if (type == Constants.Type.EVENT) {
                booking.setType("Event");
            } else {
                booking.setType("Appointment");
            }

            /* TODO Handle number of sessions */

            mAddBookingViewModel.addBooking(booking);
        } else if (v.getId() == R.id.location_button) {
            Intent intent = new Intent(getActivity(), GoogleMapsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}
