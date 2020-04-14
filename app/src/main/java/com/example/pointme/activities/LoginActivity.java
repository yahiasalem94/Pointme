package com.example.pointme.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.R;
import com.example.pointme.databinding.ActivityLoginBinding;
import com.example.pointme.viewModels.LoginViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.pointme.constants.Constants.FB_EMAIL;
import static com.example.pointme.constants.Constants.FB_PROFILE;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static int FACEBOOK_REQUEST_CODE;
    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;

    private EditText passwordText;
    private TextView signupTv;
    private ImageView iv;
    private  ActivityLoginBinding activityLoginBinding;

    private ProgressDialog progressDialog;

    private LoginViewModel mViewModel;
//    private FirebaseAuthLiveData firebaseUserLiveData;


    private String email;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setupFacebookLogin();

        mViewModel.loginState.observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                progressDialog.dismiss();
                if (firebaseUser != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    enableViews(true);
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        activityLoginBinding.fbButton.setReadPermissions(FB_EMAIL, FB_PROFILE);
//        FACEBOOK_REQUEST_CODE = activityLoginBinding.fbButton.getRequestCode();

    }

    private void setupFacebookLogin() {

    }

    public void login(View v)  {

        enableViews(false);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        email = activityLoginBinding.emailText.getText().toString().trim();
        password = activityLoginBinding.passwordInput.getText().toString().trim();
        if (validate()) {
            mViewModel.SignInWithEmail(email, password);
        } else {
            enableViews(true);
            progressDialog.dismiss();
        }
    }

    public void gotoSignup(View view) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
    public boolean validate() {
        if (TextUtils.isEmpty(email) )  {
            activityLoginBinding.emailText.setText("Email is required");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            activityLoginBinding.passwordInput.setText("Password is required");
            return false;
        }

        return true;
    }

    public void fbLogin(View view) {
        mViewModel.fbLogin(this);
    }

    public void enableViews(boolean toggle) {
        activityLoginBinding.loginButton.setEnabled(toggle);
        activityLoginBinding.loginButton.setClickable(toggle);

        activityLoginBinding.emailText.setEnabled(toggle);
        activityLoginBinding.emailText.setClickable(toggle);

        activityLoginBinding.passwordInput.setEnabled(toggle);
        activityLoginBinding.passwordInput.setClickable(toggle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.handleOnActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "request code " + requestCode + " result code " + resultCode);
    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

//    public void onLoginSuccess(boolean isProvider) {
//        if (isProvider) {
//            Intent intent = new Intent(this, ServiceProviderMainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

//    public void onLoginFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//
//        loginButton.setEnabled(true);
//    }



    public void checkUserExistence() {
//        final String user_id = mAuth.getCurrentUser().getUid();
//
//        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.hasChild(user_id)){
//                    // startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    checkServiceProvider(user_id);
//                } else {
//                    Toast.makeText(LoginActivity.this, "User not registered!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void checkServiceProvider(final String user_id) {
        final boolean result;
//        mDatabase.child("Names").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.hasChild(user_id)) {
////                    new android.os.Handler().postDelayed(
////                            new Runnable() {
////                                public void run() {
////                                    onLoginSuccess(true);
////                                }
////                            }, 300);
//                    progressDialog.dismiss();
//                    onLoginSuccess(true);
//                } else {
////                    new android.os.Handler().postDelayed(
////                            new Runnable() {
////                                public void run() {
////                                    onLoginSuccess(false);
////                                }
////                            }, 300);
//                    progressDialog.dismiss();
//                    onLoginSuccess(true);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }


}
