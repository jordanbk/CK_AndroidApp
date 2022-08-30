package com.example.course_keeper_capstone.UI.Base;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.Util.Util;

public class SignupViewModel extends AndroidViewModel {
    private Repository mRepository;

    public SignupViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getRepository(application);
    }


    private MutableLiveData<Pair<Boolean, String>> isRegisterSuccessMLD = new MutableLiveData<>(null);
    public LiveData<Pair<Boolean, String>> isRegisterSuccessLD = isRegisterSuccessMLD;

    public void register(User user) {
        if (user == null) {
            isRegisterSuccessMLD.setValue(new Pair<>(false, "Enter valid inputs!"));
        } else {
            Repository.databaseExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    user.setPassword(Util.getHashPassword(user.getPassword()));
                    long rowId = mRepository.insert(user);
                    if (rowId > -1) {
                        isRegisterSuccessMLD.postValue(new Pair<>(true, "User registered successfully!"));
                    } else {
                        isRegisterSuccessMLD.postValue(new Pair<>(false, "registration failed!"));
                    }
                }
            });
        }

    }


}
