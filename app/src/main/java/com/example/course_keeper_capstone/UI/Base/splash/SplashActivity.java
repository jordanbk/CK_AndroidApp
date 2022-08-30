package com.example.course_keeper_capstone.UI.Base.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.course_keeper_capstone.R;
import com.example.course_keeper_capstone.UI.Base.HomeActivity;
import com.example.course_keeper_capstone.UI.Base.LoginActivity;
import com.example.course_keeper_capstone.Util.Util;
import com.example.course_keeper_capstone.UI.Base.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    private com.example.course_keeper_capstone.UI.Base.splash.SplashViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mViewModel = new ViewModelProvider(this).get(com.example.course_keeper_capstone.UI.Base.splash.SplashViewModel.class);
        mViewModel.setWaitingTime();
        mViewModel.timeUp.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    if (Util.LoginPrefs.isLoggedIn(SplashActivity.this)) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        intent.putExtra("id", Util.LoginPrefs.getCurrentUserId(SplashActivity.this));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    SplashActivity.this.finish();
                }
            }
        });
    }
}