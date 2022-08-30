package com.example.course_keeper_capstone.UI.Assessments;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDetailViewModel extends AndroidViewModel {
    public LiveData<List<Course>> courseLD;
    private final Repository mRepository;

    public AssessmentDetailViewModel(@NonNull Application application, int userId) {
        super(application);
        mRepository = Repository.getRepository(application);
        courseLD = mRepository.getUserCourses(userId);
    }

    private final MutableLiveData<Pair<Boolean, String>> isUpdateSuccessMLD = new MutableLiveData<>(null);
    public LiveData<Pair<Boolean, String>> isUpdateSuccessLD = isUpdateSuccessMLD;


    public void delete(Assessment assessment) {
        Repository.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int rowId = mRepository.delete(assessment);
                if (rowId > -1) {
                    isUpdateSuccessMLD.postValue(new Pair<>(true, "Record deleted successfully!"));
                } else {
                    isUpdateSuccessMLD.postValue(new Pair<>(false, "Failed to delete record!"));
                }
            }
        });
    }

    public void updateAssessment(Assessment assessment) {
        if (assessment == null) {
            isUpdateSuccessMLD.setValue(new Pair<>(false, "Enter valid inputs!"));
        } else {
            Repository.databaseExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    int rowId = mRepository.update(assessment);
                    if (rowId > -1) {
                        isUpdateSuccessMLD.postValue(new Pair<>(true, "Record updated successfully!"));
                    } else {
                        isUpdateSuccessMLD.postValue(new Pair<>(false, "Failed to update record!"));
                    }
                }
            });
        }

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


    static class AssessmentDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory{
        private final Application application;
        private final int userId;

        public AssessmentDetailsViewModelFactory(Application application, int userId) {
            this.application = application;
            this.userId = userId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if(modelClass.isAssignableFrom(AssessmentDetailViewModel.class)){
                return (T) new AssessmentDetailViewModel(application,userId);
            }
            return super.create(modelClass);
        }
    }

}
