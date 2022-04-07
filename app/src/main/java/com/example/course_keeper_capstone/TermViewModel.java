package com.example.course_keeper_capstone;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.Entity.User;

import java.util.List;


public class TermViewModel extends AndroidViewModel {

    public LiveData<List<Term>> mTermsByUserId;
    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();
    private MutableLiveData<User> mLiveUser = new MutableLiveData();
    private Repository mRepository;
    int userID;
    int id;
    String termName;
    String termStart;
    String termEnd;

    public TermViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getDatabase(application.getApplicationContext());
        mLiveUser.getValue();
        mTermsByUserId = mRepository.getUserTerms(userID);
    }


    public LiveData<List<Term>> getmTermsByUserId(int userID){
        return mRepository.getUserTerms(userID);
    }

    public void saveUserTerm(String termName, String termStart, String termEnd, int userID) {
        Term term = mLiveTerm.getValue();

       if (term == null) {
            if (TextUtils.isEmpty(termName.trim())) {
                return;
            }
            term = new Term(termName.trim(), termStart, termEnd, userID);
        } else {
           term.setTermName(termName.trim());
           term.setTermStart(termStart);
           term.setTermEnd(termEnd);
           term.setUserID(userID);
           term = new Term(termName, termStart, termEnd, userID);
       }
        mRepository.insert(term);
        mLiveTerm.setValue(term);
    }



/*    public void saveUserTerm(Term term) {
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
            term.setUserID(userID);
        }
        mRepository.insert(term);
    }*/
}
