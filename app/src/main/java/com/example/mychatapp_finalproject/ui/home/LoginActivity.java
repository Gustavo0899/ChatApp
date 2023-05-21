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
import com.example.mychatapp_finalproject.databinding.ActivityLoginBinding;
import com.example.mychatapp_finalproject.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {


    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    EditText et_email, et_pass;
    Button btn_signup,btn_forgetPass,btn_login;
    TextView txt_signup,txt_forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.et_Email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.et_Pass, ".{6,}", R.string.invalid_password);

        et_email = findViewById(R.id.et_Email);
        et_pass = findViewById(R.id.et_Pass);
        txt_signup = findViewById(R.id.txt_signup);
        btn_login = findViewById(R.id.btnLogin);
        //btn_forgetPass = findViewById(R.id.txt_forgot_password);


        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    String mail = et_email.getText().toString();
                    String pass = et_pass.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //
                            if (task.isSuccessful()){
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                Toast.makeText(LoginActivity.this, "You are in", Toast.LENGTH_LONG).show();

                            }else{
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                Toast.makeText(LoginActivity.this, "Internal Error, Try again. Error: " + errorCode, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
//        btn_forgetPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
//    private void toHome(){
//        Intent x = new Intent(LoginActivity.this, MainActivity.class);
//        x.putExtra("mail",et_email.getText().toString());
//        x.putExtra("password",et_pass.getText().toString());
//        startActivity(x);
//    }
}