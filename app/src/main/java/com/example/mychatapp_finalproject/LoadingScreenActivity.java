package com.example.mychatapp_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mychatapp_finalproject.databinding.ActivityLoadingScreenBinding;
import com.example.mychatapp_finalproject.ui.registration.LoginActivity;

public class LoadingScreenActivity extends AppCompatActivity {
    ActivityLoadingScreenBinding binding;
    private final String TAG = "LoadingActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoadingScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(LoadingScreenActivity.this).load(R.drawable.icon_wechat_logo).into(binding.appLogo);

        startProgressBar();
    }

    private void startProgressBar() {
        // milisinFuture = total time and interval = increments every x
        LoadingCountDownTimer loadingCountDownTimer = new LoadingCountDownTimer(5000, 1000);
        loadingCountDownTimer.start();
    }

    private void startMainActivity() {
        Intent i = new Intent(LoadingScreenActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public class LoadingCountDownTimer extends CountDownTimer {
        private int progress = 0;

        public LoadingCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            progress = progress + 20;
            binding.loadingProgressBar.setProgress(progress);
        }

        @Override
        public void onFinish() { startMainActivity(); }
    }
}