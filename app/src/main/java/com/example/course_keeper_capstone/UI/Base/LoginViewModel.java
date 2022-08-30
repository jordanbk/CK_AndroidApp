package com.example.course_keeper_capstone.UI.Base;

import android.app.Application;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.Util.Util;

public class LoginViewModel extends AndroidViewModel {
    private Repository mRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getRepository(application);
    }

    private MutableLiveData<User> userMLD = new MutableLiveData<>(null);
    public LiveData<User> userLD = userMLD;

    public void getUser(User user) {
        Repository.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                User user1 = mRepository.getUser(user.getUsername(), Util.getHashPassword(user.getPassword()));
                if(user1 == null){
                    userMLD.postValue(new User());
                }else {
                    userMLD.postValue(user1);
                }
            }
        });

    }

}