package com.example.course_keeper_capstone.UI.Instructors;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Instructor;

import java.util.List;

public class InstructorViewModel extends AndroidViewModel {
    private Repository mRepository;

    public InstructorViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getRepository(application);
    }

    public LiveData<List<Instructor>> getInstructorsByUserId(int userID) {
        return  mRepository.getUserInstructors(userID);
    }
}