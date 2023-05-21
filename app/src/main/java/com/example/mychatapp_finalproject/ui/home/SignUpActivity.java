package com.example.mychatapp_finalproject.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.mychatapp_finalproject.MainActivity;
import com.example.mychatapp_finalproject.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

/*
Actividad para crear un registro de usuario
*/
public class SignUpActivity extends AppCompatActivity {

        EditText et_Email, et_Pass,et_username,et_Pass2;
        FirebaseAuth firebaseAuth;
        AwesomeValidation awesomeValidation;

        Button btn_signup;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
//        binding = ActivitySignupBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

            et_Email = findViewById(R.id.et_Email);
            et_Pass = findViewById(R.id.et_Pass);
            et_Pass2 = findViewById(R.id.et_Pass2);
            et_username = findViewById(R.id.et_username);
            //Sign-up button
            btn_signup = findViewById(R.id.btnSignUp);

            firebaseAuth = FirebaseAuth.getInstance();
            awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

            awesomeValidation.addValidation(this, R.id.et_Email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
            awesomeValidation.addValidation(this, R.id.et_Pass, ".{6,}", R.string.invalid_password);
            awesomeValidation.addValidation(this, R.id.et_Pass2, ".{6,}", R.string.invalid_password);
            awesomeValidation.addValidation(this, R.id.et_username, "[a-zA-Z\\s]+", R.string.err_name);

            btn_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mail = et_Email.getText().toString();
                    String pass = et_Pass.getText().toString();
                    String pass2 = et_Pass2.getText().toString();
                    String username = et_username.getText().toString();

                    if (awesomeValidation.validate()) {
                        if(pass.equals(pass2)) {
                            firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
//                                    startActivity(i);
                                        finish();

                                    } else {
                                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                        Toast.makeText(SignUpActivity.this, "Internal Error, Try again. Error: " + errorCode, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(SignUpActivity.this, "The password and confirmation do not match", Toast.LENGTH_SHORT).show();

                        }

                    }else{
                        Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                    }
                }

            });

        }
}