package com.example.pointme.viewModels;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class FirebaseDocumentLiveData extends LiveData<DocumentSnapshot> {
    private static final String TAG = FirebaseDocumentLiveData.class.getSimpleName();

    private DocumentReference documentReference;

    private final EventListener listener = new MyValueEventListener();
    private ListenerRegistration listenerRegistration;

    private boolean listenerRemovePending = false;
    private final Handler handler = new Handler();
    
    public FirebaseDocumentLiveData(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            listenerRegistration.remove();
            listenerRemovePending = false;
        }
    };

    @Override
    protected void onActive() {
        super.onActive();

        Log.d(TAG, "onActive");
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener);
        }
        else {
            listenerRegistration = documentReference.addSnapshotListener(listener);
        }
        listenerRemovePending = false;
    }

    @Override
    protected void onInactive() {
        super.onInactive();

        Log.d(TAG, "onInactive: ");
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }


    private class MyValueEventListener implements EventListener<DocumentSnapshot> {
        @Override
        public void onEvent(@Nullable DocumentSnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Log.e(TAG, "Can't listen to query snapshots: " + querySnapshot + ":::" + e.getMessage());
            } else {
                setValue(querySnapshot);
            }
        }
    }
}