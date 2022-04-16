package com.example.course_keeper_capstone.UI.Base;

import android.app.Application;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.User;

public class LoginViewModel extends AndroidViewModel {
    private Repository mRepository;
    int userID;
    private MutableLiveData<User> mLiveUser = new MutableLiveData();
    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getDatabase(application.getApplicationContext());
    }

    public void registerUser(int id, String email, String username, String password) {
        User user = mLiveUser.getValue();

        if (user == null) {
            if(TextUtils.isEmpty(username.trim())){
                return;
            }
            user = new User(id, email, username, password);
        }
        else {
            user.setId(id);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
        }
        mRepository.insert(user);
        mLiveUser.setValue(user);
    }

    public void authorizeUser(int id, String email, String password){
        User user = mLiveUser.getValue();
        if(user != null){
            mLiveUser.setValue(user);
        }
    }
}
