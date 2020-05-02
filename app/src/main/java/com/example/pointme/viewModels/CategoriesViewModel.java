package com.example.pointme.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pointme.constants.Constants;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.pointme.constants.Constants.APP_DATA_NODE;

public class CategoriesViewModel extends AndroidViewModel {

    private static final String TAG = CategoriesViewModel.class.getSimpleName();
    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private static final DocumentReference DOCUMENT_REFERENCE = mDatabase.collection(APP_DATA_NODE).document(Constants.SERVICES);
    private final FirebaseDocumentLiveData liveData = new FirebaseDocumentLiveData(DOCUMENT_REFERENCE);

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public LiveData<DocumentSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}

