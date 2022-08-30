package com.example.course_keeper_capstone.UI.reports;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.UI.reports.models.AssessmentR;
import com.example.course_keeper_capstone.UI.reports.models.CourseR;
import com.example.course_keeper_capstone.UI.reports.models.InstructorR;
import com.example.course_keeper_capstone.UI.reports.models.TermR;
import com.example.course_keeper_capstone.UI.reports.models.*;

import java.util.ArrayList;
import java.util.List;

public class ReportViewModel extends AndroidViewModel {
    private Repository mRepository;
    public LiveData<List<TermR>> termsLD;
    public LiveData<List<CourseR>> coursesLD;
    public LiveData<List<AssessmentR>> assessmentsLD;
    public LiveData<List<InstructorR>> instructorsLD;

    public ReportViewModel(@NonNull Application application, int userId) {
        super(application);
        mRepository = Repository.getRepository(application);
        termsLD = Transformations.switchMap(mRepository.getUserTerms(userId),
                termsList->{
                    List<TermR> list = new ArrayList<>();
                    for (com.example.course_keeper_capstone.Entity.Term item : termsList) {
                        list.add(new TermR(item.getTermID(), item.getTermName(), item.getTermStart(),item.getTermEnd()));
                    }
                    MutableLiveData<List<TermR>> termsMLD = new MutableLiveData<>();
                    termsMLD.setValue(list);
                    return termsMLD;
                });
        coursesLD = mRepository.getUserCoursesWithTerm(userId);
        assessmentsLD = mRepository.getUserAssessmentsWithCourse(userId);
        instructorsLD = mRepository.getUserInstructorsWithCourse(userId);
    }

    static class ReportViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;
        private final int userId;

        public ReportViewModelFactory(Application application, int userId) {
            this.application = application;
            this.userId = userId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ReportViewModel.class)) {
                return (T) new ReportViewModel(application, userId);
            }
            return super.create(modelClass);
        }
    }
}
