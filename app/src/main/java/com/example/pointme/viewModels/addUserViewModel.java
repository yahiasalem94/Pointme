package com.example.pointme.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pointme.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.pointme.constants.Constants.USERS_NODE;

public class addUserViewModel extends ViewModel {
    private static final String TAG = addUserViewModel.class.getSimpleName();
    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    public MutableLiveData<Boolean> addUserSuccessful = new MutableLiveData<>();

    private User user;

    public addUserViewModel(User user) {
        this.user = user;
    }

    public void addRegisteredUser(User user) {
        //Add call to authenticate through firebase
         mDatabase.collection(USERS_NODE).add(this.user).addOnSuccessListener(documentReference -> {
             Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
             addUserSuccessful.postValue(true); })
                 .addOnFailureListener(e -> {
                     Log.w(TAG, "Error adding document", e);
                });
    }
}
