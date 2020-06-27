package com.example.pointme.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.pointme.constants.Constants.BOOKINGS;
import static com.example.pointme.constants.Constants.CRID;
import static com.example.pointme.constants.Constants.EVENTS;
import static com.example.pointme.constants.Constants.SPID;
import static com.example.pointme.constants.Constants.UID;

public class ProvidersBookingViewModel extends ViewModel {
    private static final String TAG = ProvidersBookingViewModel.class.getSimpleName();
    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private static Query query;
    private FirebaseQueryLiveData liveData;

    public ProvidersBookingViewModel(String spId) {
        query = mDatabase.collection(BOOKINGS).whereEqualTo(SPID, spId);
        liveData = new FirebaseQueryLiveData(query);
    }

    @NonNull
    public LiveData<QuerySnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}