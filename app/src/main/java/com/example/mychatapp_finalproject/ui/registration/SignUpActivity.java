package com.example.mychatapp_finalproject.ui.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.mychatapp_finalproject.R;

import com.example.mychatapp_finalproject.database.IDatabaseHelper;
import com.example.mychatapp_finalproject.database.ServiceLocator;
import com.example.mychatapp_finalproject.model.Model;
import com.example.mychatapp_finalproject.model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private final String TAG = "SignUpActivity";
    EditText etEmail, etPass, etUsername, etPass2;
    Button btnSignup;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        etPass2 = findViewById(R.id.et_pass2);
        etUsername = findViewById(R.id.et_username);

        btnSignup = findViewById(R.id.btn_sign_up);

        firebaseAuth = ServiceLocator.getInstance().getFirebaseAuth();
        awesomeValidation = ServiceLocator.getInstance().getValidator();

        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.et_pass, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.et_pass2, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.et_username, "[a-zA-Z\\s]+", R.string.err_name);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                String pass2 = etPass2.getText().toString();
                String username = etUsername.getText().toString();

                if (awesomeValidation.validate()) {
                    if (pass.equals(pass2)) {
                        firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    createUserProfile(username);
                                    Log.d(TAG, "Registration Successful");
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Internal Error. Try again", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Internal error: " + task.getException());
                                }
                            }
                        });
                    } else {
                        Toast.makeText(SignUpActivity.this, "The password and confirmation do not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUserProfile(String username) {
        IDatabaseHelper databaseHelper = ServiceLocator.getInstance().getDatabase();
        FirebaseUser user = ServiceLocator.getInstance().getFirebaseAuth().getCurrentUser();
        if (user != null) {
            UserProfile userProfile = ServiceLocator.getInstance().getNewUserProfile();
            userProfile.setId(user.getUid());
            userProfile.setUsername(username);
            userProfile.setEmail(user.getEmail());
            Log.d(TAG, "User id: " + user.getUid() + "\nUsername: " + username);
            databaseHelper.create(user.getUid(), Model.USER_PROFILE, userProfile);
            Log.d(TAG, "User profile created");
        } else {
            Log.d(TAG, "No logged-in user found");
        }
    }
}