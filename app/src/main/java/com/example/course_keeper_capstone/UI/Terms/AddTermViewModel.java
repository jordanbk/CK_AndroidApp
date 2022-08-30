package com.example.course_keeper_capstone.UI.Terms;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Term;

public class AddTermViewModel extends AndroidViewModel {
    private Repository mRepository;

    public AddTermViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getRepository(application);
    }

    private MutableLiveData<Pair<Boolean, String>> isSaveSuccessMLD = new MutableLiveData<>(null);
    public LiveData<Pair<Boolean, String>> isSaveSuccessLD = isSaveSuccessMLD;

    public void saveUserTerm(Term term) {
        if (term == null) {
            isSaveSuccessMLD.setValue(new Pair<>(false, "Enter valid inputs!"));
        } else {
            Repository.databaseExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    long rowId = mRepository.insert(term);
                    if (rowId > -1) {
                        isSaveSuccessMLD.postValue(new Pair<>(true, "Record inserted successfully!"));
                    } else {
                        isSaveSuccessMLD.postValue(new Pair<>(false, "Failed to insert record!"));
                    }
                }
            });
        }

    }


}
