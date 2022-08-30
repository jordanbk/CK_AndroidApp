package com.example.course_keeper_capstone.UI.Courses;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.R;

import java.util.ArrayList;
import java.util.List;

public class AddCourseViewModel extends AndroidViewModel {
    private Repository mRepository;
    public LiveData<List<Term>> termLD;

    public AddCourseViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getRepository(application);
        termLD = mRepository.getAllTerms();
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

    private MutableLiveData<Pair<Boolean, String>> isSaveSuccessMLD = new MutableLiveData<>(null);
    public LiveData<Pair<Boolean, String>> isSaveSuccessLD = isSaveSuccessMLD;

    public void addCourse(Course course) {
        Repository.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long rowId = mRepository.insert(course);
                if (rowId > -1) {
                    isSaveSuccessMLD.postValue(new Pair<>(true, "Record inserted successfully!"));
                } else {
                    isSaveSuccessMLD.postValue(new Pair<>(false, "Failed to insert record!"));
                }
            }
        });
    }

}
