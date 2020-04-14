package com.example.pointme.viewModels;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import static com.example.pointme.constants.Constants.FB_EMAIL;
import static com.example.pointme.constants.Constants.FB_PROFILE;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();
//    private final FirebaseAuthLiveData firebaseAuthLiveData = new FirebaseAuthLiveData();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CallbackManager mCallbackManager;
    private LoginManager loginManager;

    public MutableLiveData<FirebaseUser> loginState = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

//        public LiveData<FirebaseUser> getFirebaseAuthLiveData() {
//            return firebaseAuthLiveData;
//        }

    public void SignInWithEmail(String email, String password) {
        //Add call to authenticate through firebase
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
               loginState.postValue(mAuth.getCurrentUser());
            }else {
                loginState.postValue(mAuth.getCurrentUser());
            }
        });
    }

    public void fbLogin(Activity activity) {
        mCallbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                if (AccessToken.getCurrentAccessToken() != null) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
        loginManager.logInWithReadPermissions(activity, Arrays.asList(FB_EMAIL, FB_PROFILE));
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginState.postValue(mAuth.getCurrentUser());
            } else {
                loginState.postValue(mAuth.getCurrentUser());
            }
        });
    }

    public void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            Log.d(TAG,"Result should be handled");
        }
    }

    public void isUserRegistered(String email, String password) {
        //Add call to authenticate through firebase
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                loginState.postValue(mAuth.getCurrentUser());
            }else {
                loginState.postValue(mAuth.getCurrentUser());
            }
        });
    }
}