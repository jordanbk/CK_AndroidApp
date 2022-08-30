package com.example.course_keeper_capstone;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.course_keeper_capstone.Database.Repository;
import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Instructor;
import com.example.course_keeper_capstone.Entity.Term;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditViewModel extends AndroidViewModel {
    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();
    public MutableLiveData<Course> mLiveCourse = new MutableLiveData<>();
    public MutableLiveData<Assessment> mLiveAssessment = new MutableLiveData<>();
    public MutableLiveData<Instructor> mLiveInstructor = new MutableLiveData<>();
    public LiveData<List<Term>> mAllTerms;
    public LiveData<List<Course>> mAllCourses;
    public LiveData<List<Assessment>> mAllAssessments;
    public LiveData<List<Instructor>> mAllInstructors;
    private Repository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getDatabase(getApplication());
        mAllTerms = mRepository.getAllTerms();
        mAllCourses = mRepository.getAllCourses();
        mAllAssessments = mRepository.getAllAssessments();
        mAllInstructors = mRepository.getAllInstructors();
    }


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
    }

    public void saveCourse(String courseTitle,String courseStatus, String courseStart, String courseEnd, String courseNotes) {
        Course course = mLiveCourse.getValue();

        if(course == null) {
            if (TextUtils.isEmpty(courseTitle.trim())) {
                return;
            }
            course = new Course(courseTitle.trim(), courseStatus, courseStart, courseEnd, courseNotes);
        } else {
            course.setCourseTitle(courseTitle.trim());
            course.setCourseStatus(courseStatus);
            course.setCourseStart(courseStart);
            course.setCourseEnd(courseEnd);
            course.setCourseNotes(courseNotes);
        }
        mRepository.insert(course);
    }

    public void overwriteCourse(Course course, int termId) {
        course.setTermID_FK(termId);
        mRepository.insert(course);
    }

    public void overwriteAssessment(Assessment assessment, int courseId) {
        assessment.setCourseID_FK(courseId);
        mRepository.insert(assessment);
    }

    public void overwriteMentor(Instructor instructor, int courseId) {
        instructor.setCourseID(courseId);
        mRepository.insert(instructor);
    }

    public void saveAssessment(String assessmentTitle, String assessmentStartDate, String assessmentEndDate, String assessmentKind, int courseID_FK) {
        Assessment assessment = mLiveAssessment.getValue();

        if(assessment == null) {
            if(TextUtils.isEmpty(assessmentTitle.trim())) {
                return;
            }
            assessment = new Assessment(assessmentTitle.trim(), assessmentStartDate, assessmentEndDate, assessmentKind, courseID_FK);
        } else {
            assessment.setAssessmentTitle(assessmentTitle.trim());
            assessment.setAssessmentStartDate(assessmentStartDate);
            assessment.setAssessmentKind(assessmentKind);
            assessment.setCourseID_FK(courseID_FK);
        }
        mRepository.insert(assessment);
    }

    public void saveInstructor(String instructorName, String instructorEmail, String instructorPhone, int courseID) {
        Instructor instructor = mLiveInstructor.getValue();

        if(instructor == null) {
            if(TextUtils.isEmpty(instructorName.trim())) {
                return;
            }
            instructor = new Instructor(instructorName, instructorEmail, instructorPhone, courseID);
        } else {
            instructor.setInstructorName(instructorName);
            instructor.setInstructorEmail(instructorEmail);
            instructor.setInstructorPhone(instructorPhone);
            instructor.setInstructorID(courseID);
        }
        mRepository.insert(instructor);
    }

    public void deleteTerm() {
        mRepository.delete(mLiveTerm.getValue());
    }

    public void deleteCourse() {
        mRepository.delete(mLiveCourse.getValue());
    }

    public void deleteAssessment() {
        mRepository.delete(mLiveAssessment.getValue());
    }

/*    public LiveData<List<Course>> getCoursesInTerm(int termId) {
        return (mRepository.getCoursesByTerm(termId));
    }

    public LiveData<List<Assessment>> getAssessmentsInCourse(int courseId) {
        return (mRepository.getAssessmentsByCourse(courseId));
    }

    public LiveData<List<Mentor>> getMentorsInCourse(int courseId) {
        return (mRepository.getMentorsByCourse(courseId));
    }

    public LiveData<List<Course>> getUnassignedCourses() {
        return (mRepository.getCoursesByTerm(-1));
    }

    public LiveData<List<Assessment>> getUnassignedAssessments() {
        return (mRepository.getAssessmentsByCourse(-1));
    }

    public LiveData<List<Mentor>> getUnassignedMentors() {
        return (mRepository.getMentorsByCourse(-1));
    }

    public Term getTermById(int termId) {
        return mRepository.getTermById(termId);
    }*/
}
