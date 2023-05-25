package com.example.mychatapp_finalproject.ui.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.mychatapp_finalproject.MainActivity;
import com.example.mychatapp_finalproject.R;
import com.example.mychatapp_finalproject.database.ServiceLocator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    EditText etEmail, etPass;
    Button btnForgetPass, btnLogin;
    TextView txtForgotPassword, txtSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = ServiceLocator.getInstance().getFirebaseAuth();
        awesomeValidation = ServiceLocator.getInstance().getValidator();
        awesomeValidation.addValidation(this, R.id.et_email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.et_email, ".{6,}", R.string.invalid_password);

        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        txtSignup = findViewById(R.id.txt_sign_up);
        btnLogin = findViewById(R.id.btn_login);
        //btnForgetPass = findViewById(R.id.txt_forgot_password);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    try {
                        String mail = etEmail.getText().toString();
                        String pass = etPass.getText().toString();

                        firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    Toast.makeText(LoginActivity.this, "You are in", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "You are in");

                                } else {
                                    Toast.makeText(LoginActivity.this, "Internal Error. Try again", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Internal error: " + task.getException());
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


//        btnForgetPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}