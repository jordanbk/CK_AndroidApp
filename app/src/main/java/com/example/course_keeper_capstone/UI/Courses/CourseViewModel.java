package com.example.course_keeper_capstone.UI.Courses;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.Entity.User;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    public LiveData<List<Course>> mCoursesByUserId;
    public MutableLiveData<Course> mLiveCourse = new MutableLiveData<>();
    private MutableLiveData<User> mLiveUser = new MutableLiveData();
    private Repository mRepository;
    int userID;
    int id;
    String courseName;
    String courseStatus;
    String courseStart;
    String courseEnd;
    String courseNotes;
    int termID;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getDatabase(application.getApplicationContext());
        mLiveUser.getValue();
        mCoursesByUserId = mRepository.getUserCourses(userID);
    }

    private MutableLiveData<List<Course>> _coursesByUserData = new MutableLiveData<>();
    public LiveData<List<Course>> coursesByUserData = _coursesByUserData;

    public LiveData<List<Course>> getmCoursesByUserId(int userID){
        return  mRepository.getUserCourses(userID);

    }

    public LiveData<List<Term>> searchTerms(String searchQuery){
        return mRepository.searchTerms(searchQuery);
    }

    public void saveUserCourse(String courseName, String courseStatus, String courseStart, String courseEnd, String courseNotes, int userID) {
        Course course = mLiveCourse.getValue();

        if (course == null) {
            if (TextUtils.isEmpty(courseName.trim())) {
                return;
            }
        } else {
            course.setCourseTitle(courseName.trim());
            course.setCourseStatus(courseStatus);
            course.setCourseStart(courseStart);
            course.setCourseEnd(courseEnd);
            course.setCourseNotes(courseNotes);
            course.setUserID(userID);
        }
        mRepository.insert(course);
        mLiveCourse.setValue(course);
    }

}

