package com.example.course_keeper_capstone.UI.Assessments;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;

import java.util.ArrayList;
import java.util.List;

public class AddAssessmentViewModel extends AndroidViewModel {
    public LiveData<List<Course>> courseLD;
    private final Repository mRepository;

    public AddAssessmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getRepository(application);
        courseLD = mRepository.getAllCourses();
    }

    /**
     * Return List of CourseR Titles from List of CourseR Objects
     */
    public LiveData<List<String>> getCourseTitles() {
        return Transformations.switchMap(courseLD, courseList -> {
            List<String> list = new ArrayList<>();
            list.add(getApplication().getResources().getStringArray(R.array.courses)[0]);
            for (Course course : courseList) {
                list.add(course.getCourseTitle());
            }
            MutableLiveData<List<String>> courseMLD = new MutableLiveData<>();
            courseMLD.setValue(list);
            return courseMLD;
        });
    }


    private MutableLiveData<Pair<Boolean, String>> isSaveSuccessMLD = new MutableLiveData<>(null);
    public LiveData<Pair<Boolean, String>> isSaveSuccessLD = isSaveSuccessMLD;


    public void addAssessment(Assessment assessment) {
        Repository.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long rowId = mRepository.insert(assessment);
                if (rowId > -1) {
                    isSaveSuccessMLD.postValue(new Pair<>(true, "Record inserted successfully!"));
                } else {
                    isSaveSuccessMLD.postValue(new Pair<>(false, "Failed to insert record!"));
                }
            }
        });
    }






}
