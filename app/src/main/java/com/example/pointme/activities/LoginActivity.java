package com.example.pointme.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pointme.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static int FACEBOOK_REQUEST_CODE;
    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private LoginButton fbButton;
    private TextView signupTv;
    private ImageView iv;

    private ProgressDialog progressDialog;
    CallbackManager mCallbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btn_login);
        fbButton = findViewById(R.id.fbbutton);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        signupTv = findViewById(R.id.signupTv);
        iv = findViewById(R.id.iv);

        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCallbackManager = CallbackManager.Factory.create();

        fbButton.setReadPermissions("email", "public_profile");
        FACEBOOK_REQUEST_CODE = fbButton.getRequestCode();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        fbButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d(TAG,"Hello"+loginResult.getAccessToken().getToken());
                //  Toast.makeText(MainActivity.this, "Token:"+loginResult.getAccessToken(), Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"operation cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, error.toString());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login(mAuth);
            }
        });

        signupTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    private void  handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "check existence");
                            onLoginSuccess(false);
                        }


                    }
                });
    }

    private void login(FirebaseAuth mAuth) {
        Log.d(TAG, "Login");

        loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        if (validate()){

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                       checkUserExistence();
                    }else {
                        loginButton.setEnabled(true);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Couldn't login, User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);
            Toast.makeText(LoginActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                onLoginSuccess(false);
            }
        }
        else if (requestCode == FACEBOOK_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult"+ " " + "Facebook request");
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(boolean isProvider) {
        if (isProvider) {
            Intent intent = new Intent(this, ServiceProviderMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (TextUtils.isEmpty(email)&& TextUtils.isEmpty(password))
        {
            valid = false;
        }
        return valid;
    }

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
