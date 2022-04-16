package com.example.course_keeper_capstone.UI.Terms;

import android.app.Application;
import android.text.TextUtils;

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

    public LiveData<List<Course>> getTermCourses(int termID){
        return mRepository.getTermCourses(termID);
    }


    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();

    public void saveTerm(String termName, String termStart, String termEnd) {
        Term term = mLiveTerm.getValue();

        if (term == null) {
            if (TextUtils.isEmpty(termName.trim())) {
                return;
            }
            term = new Term(termName.trim(), termStart, termEnd);
        } else {
            term.setTermName(termName.trim());
            term.setTermStart(termStart);
            term.setTermEnd(termEnd);
        }
        mRepository.insert(term);
        mLiveTerm.setValue(term);
    }


    public void update(Term term){
       mRepository.update(term);
    }

}
