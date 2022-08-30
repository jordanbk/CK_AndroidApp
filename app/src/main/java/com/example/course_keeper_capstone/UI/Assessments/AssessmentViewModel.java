package com.example.course_keeper_capstone.UI.Assessments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Assessment;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private Repository mRepository;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getRepository(application);
    }

    public LiveData<List<Assessment>> getAssessmentsByUserId(int userID) {
        return  mRepository.getUserAssessments(userID);
    }
}
