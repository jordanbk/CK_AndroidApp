package com.example.course_keeper_capstone.UI.Courses;

import android.app.Application;
import android.text.TextUtils;
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
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailViewModel extends AndroidViewModel {
    public LiveData<List<Term>> termLD;
    private Repository mRepository;
    public CourseDetailViewModel(@NonNull Application application, int userId) {
        super(application);
        mRepository = Repository.getDatabase(application.getApplicationContext());
        termLD = mRepository.getUserTerms(userId);
    }

    private MutableLiveData<Pair<Boolean, String>> isUpdateSuccessMLD = new MutableLiveData<>(null);
    public LiveData<Pair<Boolean, String>> isUpdateSuccessLD = isUpdateSuccessMLD;

    public void update(Course course){
        if (course == null) {
            isUpdateSuccessMLD.setValue(new Pair<>(false, "Enter valid inputs!"));
        } else {
            Repository.databaseExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    int rowId = mRepository.update(course);
                    if (rowId > -1) {
                        isUpdateSuccessMLD.postValue(new Pair<>(true, "Record updated successfully!"));
                    } else {
                        isUpdateSuccessMLD.postValue(new Pair<>(false, "Failed to update record!"));
                    }
                }
            });
        }

    }

    public void delete(Course course) {
        Repository.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int rowId = mRepository.delete(course);
                if (rowId > -1) {
                    isUpdateSuccessMLD.postValue(new Pair<>(true, "Record deleted successfully!"));
                } else {
                    isUpdateSuccessMLD.postValue(new Pair<>(false, "Failed to delete record!"));
                }
            }
        });
    }

    public LiveData<List<Assessment>> getAssessmentsForCourse(int courseId, int userId){
        return  mRepository.getAssessmentsForCourse(courseId,userId);
    }



    /**
     * Return List of TermR Titles from List of TermR Objects
     */
    public LiveData<List<String>> getTermsTitles() {
        return Transformations.switchMap(termLD, termsList -> {
            List<String> termTitles = new ArrayList<>();
            termTitles.add(getApplication().getResources().getStringArray(R.array.terms)[0]);
            for (Term term : termsList) {
                termTitles.add(term.getTermName());
            }
            MutableLiveData<List<String>> termTitlesMLD = new MutableLiveData<>();
            termTitlesMLD.setValue(termTitles);
            return termTitlesMLD;
        });
    }

    static class CourseDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory{
        private final Application application;
        private final int userId;

        public CourseDetailViewModelFactory(Application application, int userId) {
            this.application = application;
            this.userId = userId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if(modelClass.isAssignableFrom(CourseDetailViewModel.class)){
                return (T) new CourseDetailViewModel(application,userId);
            }
            return super.create(modelClass);
        }
    }

}
