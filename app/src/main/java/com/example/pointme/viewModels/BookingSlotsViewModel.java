package com.example.pointme.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pointme.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.example.pointme.constants.Constants.GET_BOOKING_SLOTS;
import static com.example.pointme.constants.Constants.USERS_NODE;

public class BookingSlotsViewModel extends AndroidViewModel {
    private static final String TAG = ProvidersViewModel.class.getSimpleName();
    private FirebaseFunctions mFunctions;
    public MediatorLiveData<ArrayList<String>> bookingSlots = new MediatorLiveData<>();
    private HashMap<String, ArrayList<String>> result;

    public BookingSlotsViewModel(@NonNull Application application) {
        super(application);
        mFunctions = FirebaseFunctions.getInstance();
    }


    public void getBookingSlots(Map<String, String> data) {
        mFunctions
                .getHttpsCallable(GET_BOOKING_SLOTS)
                .call(data)
                .addOnSuccessListener(httpsCallableResult -> {
                    result = (HashMap<String, ArrayList<String>>) httpsCallableResult.getData();
                    bookingSlots.postValue(result.get("bookingSlots"));
                }).addOnFailureListener(e -> bookingSlots.postValue(null));
    }
}
