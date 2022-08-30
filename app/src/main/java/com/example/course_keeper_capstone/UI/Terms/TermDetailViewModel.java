package com.example.course_keeper_capstone.UI.Terms;

import android.app.Application;
import android.text.TextUtils;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;

import java.util.List;

public class TermDetailViewModel extends AndroidViewModel {
    private Repository mRepository;
    public TermDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getDatabase(application.getApplicationContext());
    }


    private MutableLiveData<Pair<Boolean, String>> isUpdateSuccessMLD = new MutableLiveData<>(null);
    public LiveData<Pair<Boolean, String>> isUpdateSuccessLD = isUpdateSuccessMLD;

    public void update(Term term){
        if (term == null) {
            isUpdateSuccessMLD.setValue(new Pair<>(false, "Enter valid inputs!"));
        } else {
            Repository.databaseExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    int rowId = mRepository.update(term);
                    if (rowId > -1) {
                        isUpdateSuccessMLD.postValue(new Pair<>(true, "Record updated successfully!"));
                    } else {
                        isUpdateSuccessMLD.postValue(new Pair<>(false, "Failed to update record!"));
                    }
                }
            });
        }

    }

    public void deleteTerm(Term term) {
        Repository.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int rowId = mRepository.delete(term);
                if (rowId > -1) {
                    isUpdateSuccessMLD.postValue(new Pair<>(true, "Record deleted successfully!"));
                } else {
                    isUpdateSuccessMLD.postValue(new Pair<>(false, "Failed to delete record!"));
                }
            }
        });
    }


    public LiveData<List<Course>> getTermCourses(int termID, int userId) {
        return mRepository.getTermCourses(termID,userId);
    }
}
