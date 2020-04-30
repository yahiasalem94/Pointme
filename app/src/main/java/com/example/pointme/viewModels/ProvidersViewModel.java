package com.example.pointme.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pointme.constants.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.pointme.constants.Constants.APP_DATA_NODE;
import static com.example.pointme.constants.Constants.SERVICE;
import static com.example.pointme.constants.Constants.SERVICE_PROVIDERS;

public class ProvidersViewModel extends ViewModel {

    private static final String TAG = ProvidersViewModel.class.getSimpleName();
    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private static Query query;
    private FirebaseProvidersQueryLiveData liveData;

    public ProvidersViewModel(String service)
    {
        query = mDatabase.collection(SERVICE_PROVIDERS).whereEqualTo(SERVICE, service);
        liveData = new FirebaseProvidersQueryLiveData(query);
    }

    @NonNull
    public LiveData<QuerySnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}

