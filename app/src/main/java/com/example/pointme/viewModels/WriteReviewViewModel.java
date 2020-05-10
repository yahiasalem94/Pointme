package com.example.pointme.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pointme.models.Reviews;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.pointme.constants.Constants.REVIEWS;

public class WriteReviewViewModel extends AndroidViewModel {
    private static final String TAG = WriteReviewViewModel.class.getSimpleName();
    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    public MutableLiveData<Boolean> reviewAdded = new MutableLiveData<>();

    public WriteReviewViewModel(@NonNull Application application) {
        super(application);
    }


    public void addReview(Reviews review) {
        //Add call to authenticate through firebase
        mDatabase.collection(REVIEWS).add(review).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            reviewAdded.postValue(true); })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding document", e);
                    reviewAdded.postValue(false);
                });
    }
}
