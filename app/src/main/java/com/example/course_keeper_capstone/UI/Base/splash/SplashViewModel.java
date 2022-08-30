package com.example.course_keeper_capstone.UI.Base.splash;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.course_keeper_capstone.Database.Repository;

public class SplashViewModel extends AndroidViewModel {
    private static final int WAIT_TIME = 2500;
    private MutableLiveData<Boolean> _timeUp = new MutableLiveData<>(false);
    public LiveData<Boolean> timeUp = _timeUp;

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    public void setWaitingTime() {
        new CountDownTimer(WAIT_TIME, 2500) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                _timeUp.postValue(true);
            }
        }.start();
    }

}
