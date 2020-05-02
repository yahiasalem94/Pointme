package com.example.pointme.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.pointme.constants.Constants.APPOINTMENTS;
import static com.example.pointme.constants.Constants.EVENTS;
import static com.example.pointme.constants.Constants.UID;

public class EventsViewModel extends ViewModel {
    private static final String TAG = ProvidersViewModel.class.getSimpleName();
    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private static Query query;
    private FirebaseQueryLiveData liveData;

    public EventsViewModel(String uId) {
        query = mDatabase.collection(EVENTS).whereEqualTo(UID, uId);
        liveData = new FirebaseQueryLiveData(query);
    }

    @NonNull
    public LiveData<QuerySnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}