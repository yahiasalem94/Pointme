package com.example.pointme.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.pointme.R;
import com.example.pointme.databinding.ActivitySignupBinding;
import com.example.pointme.models.User;
import com.example.pointme.viewModels.LoginViewModel;
import com.example.pointme.viewModels.addUserViewModel;
import com.example.pointme.viewModels.addUserViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private ActivitySignupBinding activitySignupBinding;
    private ProgressDialog progressDialog;

    private addUserViewModel mViewModel;
    private LoginViewModel mLoginViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    private String name;
    private String email;
    private String password;
    private String mobile;
    private String address;

    private User user;
    boolean isSignupSuccessful = false;
//    private FirestoreViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        activitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
//        viewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();



        mLoginViewModel.loginState.observe(this, firebaseUser -> {
            progressDialog.dismiss();
            if (firebaseUser != null) {
                user = new User(name, "", email);
                final addUserViewModelFactory factory = new addUserViewModelFactory(user);
                mViewModel = new ViewModelProvider(SignupActivity.this, factory).get(addUserViewModel.class);
                mViewModel.addRegisteredUser(user);
                addUserObserver();
            } else {
                enableViews(true);
                onSignupFailed();
                Toast.makeText(SignupActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserObserver() {
        mViewModel.addUserSuccessful.observe(this, isSuccessful -> {
            if (isSuccessful) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void signup(View view) {
        Log.d(TAG, "Signing up");

        enableViews(false);

          name = activitySignupBinding.inputName.getText().toString();
          email= activitySignupBinding.inputEmail.getText().toString();
          password= activitySignupBinding.inputPassword.getText().toString();
          mobile= activitySignupBinding.inputMobile.getText().toString();
          address= activitySignupBinding.inputAddress.getText().toString();

        if (!validate()) {
            onSignupFailed();
            enableViews(true);
            progressDialog.dismiss();
            return;
        }

        /* Move to layout */
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();







//
//                .addOnSuccessListener(documentReference -> {
//                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    onSignupSuccess();
//                })
//                .addOnFailureListener(e -> {
//                    Log.w(TAG, "Error adding document", e);
//                    onSignupFailed();
//                });

        mLoginViewModel.isUserRegistered(email, password);
    }


    public void gotologin(View view) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignupSuccess() {
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        enableViews(true);
    }

    public boolean validate() {
        boolean valid = true;

        if (TextUtils.isEmpty(name) || name.length() < 3) {
            activitySignupBinding.inputName.setError("at least 3 characters");
            valid = false;
        }

        if (TextUtils.isEmpty(email)) {
            activitySignupBinding.inputEmail.setError("enter a valid email address");
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            activitySignupBinding.inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }

    public void enableViews(boolean toggle) {
        activitySignupBinding.inputName.setEnabled(toggle);
        activitySignupBinding.inputEmail.setEnabled(toggle);
        activitySignupBinding.inputPassword.setEnabled(toggle);
        activitySignupBinding.inputMobile.setEnabled(toggle);
        activitySignupBinding.inputAddress.setEnabled(toggle);
    }
}
