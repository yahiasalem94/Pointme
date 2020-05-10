package com.example.pointme.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.R;
import com.example.pointme.databinding.FragmentWriteReviewBinding;
import com.example.pointme.models.Booking;
import com.example.pointme.models.Reviews;
import com.example.pointme.viewModels.LoginViewModel;
import com.example.pointme.viewModels.WriteReviewViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

import static com.example.pointme.activities.MainActivity.BOOKING;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteReviewFragment extends Fragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    private Booking booking;

    private float qualityRatingValue;
    private float puncRatingValue;
    private float attitudeRatingValue;
    private Timestamp timestamp;
    private Date date;
    /* Views */
    private FragmentWriteReviewBinding fragmentWriteReviewBinding;
    private ProgressDialog progressDialog;

    private WriteReviewViewModel mWriteReviewViewModel;
    private FirebaseAuth mAuth;

    public WriteReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            booking = getArguments().getParcelable(BOOKING);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentWriteReviewBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_write_review, container, false);
        View mRootView = fragmentWriteReviewBinding.getRoot();
        fragmentWriteReviewBinding.submitReview.setOnClickListener(this);
        fragmentWriteReviewBinding.qualityRating.setOnRatingBarChangeListener(this);
        fragmentWriteReviewBinding.puncRating.setOnRatingBarChangeListener(this);
        fragmentWriteReviewBinding.attitudeRating.setOnRatingBarChangeListener(this);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentWriteReviewBinding.meetingName.setText(booking.getMeetingName());
        fragmentWriteReviewBinding.spName.setText(booking.getSpName());
        fragmentWriteReviewBinding.date.setText(booking.getBookingDetails().get(0).getDate());
        fragmentWriteReviewBinding.time.setText(booking.getBookingDetails().get(0).getTime());

        mWriteReviewViewModel = new ViewModelProvider(WriteReviewFragment.this).get(WriteReviewViewModel.class);
        addObserver();
    }


    private void addObserver() {
        mWriteReviewViewModel.reviewAdded.observe(getViewLifecycleOwner(), isSuccessful -> {
            progressDialog.dismiss();
            if (isSuccessful) {
                Toast.makeText(getActivity(), "Review Sumbitted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error review not submitted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_review) {
            progressDialog = ProgressDialog.show(getActivity(),
                    "ProgressDialog", "Submitting Review");
            Reviews review = new Reviews();
            date = new Date();
            timestamp = new Timestamp(date);

            review.setQualityRating((int) qualityRatingValue);
            review.setPuncRating((int) puncRatingValue);
            review.setAttitudeRating((int) attitudeRatingValue);
            review.setMeetingName(booking.getMeetingName());
            review.setAvgRating((int) ((qualityRatingValue + puncRatingValue + attitudeRatingValue) / 3));
            review.setCrID(booking.getCrID());
            review.setSpID(booking.getSpID());
            review.setSpName(booking.getSpName());
            review.setMeetingID(booking.getMeetingID());
            review.setCrName(mAuth.getCurrentUser().getDisplayName());
            review.setReviewText(fragmentWriteReviewBinding.addreviewText.getText().toString());
            review.setCreated(timestamp);
            mWriteReviewViewModel.addReview(review);
        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (fromUser) {
            if (ratingBar.getId() == R.id.quality_rating) {
                qualityRatingValue = rating;
            } else if (ratingBar.getId() == R.id.punc_rating) {
                puncRatingValue = rating;
            } else if (ratingBar.getId() == R.id.attitude_rating) {
                attitudeRatingValue = rating;
            }
        }
    }
}
