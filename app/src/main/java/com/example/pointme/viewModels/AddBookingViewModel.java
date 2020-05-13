package com.example.pointme.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pointme.models.Booking;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.pointme.constants.Constants.BOOKINGS;

public class AddBookingViewModel extends AndroidViewModel {
    private static final String TAG = AddBookingViewModel.class.getSimpleName();
    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    public MutableLiveData<Boolean> bookingAdded = new MutableLiveData<>();

    public AddBookingViewModel(@NonNull Application application) {
        super(application);
    }


    public void addBooking(Booking booking) {
        //Add call to authenticate through firebase
        mDatabase.collection(BOOKINGS).add(booking).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            bookingAdded.postValue(true); })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding document", e);
                    bookingAdded.postValue(false);
                });
    }
}