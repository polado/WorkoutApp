package com.example.workoutapp.ui;

import android.content.Intent;
import android.os.Handler;

import com.example.workoutapp.R;
import com.example.workoutapp.base.BaseActivity;
import com.example.workoutapp.databinding.ActivitySplashBinding;
import com.example.workoutapp.ui.home.HomeActivity;
import com.example.workoutapp.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends BaseActivity {
    ActivitySplashBinding binding;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreated() {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        }, 2000);
    }
}
