package com.example.course_keeper_capstone.Database;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.course_keeper_capstone.DAO.AssessmentDAO;
import com.example.course_keeper_capstone.DAO.CourseDAO;
import com.example.course_keeper_capstone.DAO.InstructorDAO;
import com.example.course_keeper_capstone.DAO.TermDAO;
import com.example.course_keeper_capstone.DAO.UserDAO;
import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Instructor;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.Entity.User;
import com.example.course_keeper_capstone.UI.reports.models.AssessmentR;
import com.example.course_keeper_capstone.UI.reports.models.CourseR;
import com.example.course_keeper_capstone.UI.reports.models.InstructorR;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository ourInstance;

    private UserDAO mUserDAO;
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private InstructorDAO mInstructorDAO;
    private List<User> mAllUsers;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Assessment>> mAllAssessments;
    private LiveData<List<Instructor>> mAllInstructors;

    private static int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Repository getDatabase(Context context) {
        return getRepository((Application) context);
    }

    public static Repository getRepository(Application application) {
        if (ourInstance == null) {
            ourInstance = new Repository(application);
        }
        return ourInstance;
    }

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mUserDAO = db.userDAO();
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mInstructorDAO = db.instructorDAO();
    }


    public LiveData<List<Term>> getUserTerms(int id) {
        return mTermDAO.getUserTerms(id);
    }

    public LiveData<List<Course>> getUserCourses(int id) {
        return mCourseDAO.getUserCourses(id);
    }

    public LiveData<List<Term>> searchTerms(String searchQuery) {
        return mTermDAO.searchTerms(searchQuery);
    }

    public LiveData<List<Assessment>> getUserAssessments(int userID) {
        return mAssessmentDAO.getUserAssessments(userID);
    }

    public LiveData<List<Instructor>> getUserInstructors(int userID) {
        return mInstructorDAO.getUserInstructors(userID);
    }

    public List<User> getAllUsers() {
        databaseExecutor.execute(() -> {
            mAllUsers = mUserDAO.getAllUsers();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllUsers;
    }

    public User getUser(String username, String password) {
        return mUserDAO.getUser(username, password);
    }

    /**
     * Get All Terms
     */
    public LiveData<List<Term>> getAllTerms() {
        return mTermDAO.getAllTerms();
    }

    /**
     * Insert Terms in db
     *
     * @param term
     */
    public long insert(Term term) {
        return mTermDAO.insert(term);
    }

    /**
     * Update Terms in db
     *
     * @param term
     */
    public int update(Term term) {
        return mTermDAO.update(term);
    }

    /**
     * Delete Terms in db
     *
     * @param term
     */
    public int delete(Term term) {
        return mTermDAO.delete(term);
    }

    /**
     * Get all Courses
     *
     * @return
     */
    public LiveData<List<Course>> getAllCourses() {
        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    /**
     * Insert Courses in db
     *
     * @param course
     */
    public long insert(Course course) {
        return mCourseDAO.insert(course);
    }

    /**
     * Update Courses in db
     *
     * @param course
     */
    public int update(Course course) {
        return mCourseDAO.update(course);
    }

    /**
     * Delete Courses in db
     *
     * @param course
     */
    public int delete(Course course) {
        return mCourseDAO.delete(course);
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    /**
     * Insert Assessments in db
     *
     * @param assessment
     */
    public long insert(Assessment assessment) {
        return mAssessmentDAO.insert(assessment);
    }

    /**
     * Update Assessments in db
     *
     * @param assessment
     */
    public int update(Assessment assessment) {
        return mAssessmentDAO.update(assessment);
    }

    /**
     * Delete Assessments in db
     *
     * @param assessment
     */
    public int delete(Assessment assessment) {
        return mAssessmentDAO.delete(assessment);
    }

    /**
     * @return
     */
    public LiveData<List<Instructor>> getAllInstructors() {
        databaseExecutor.execute(() -> {
            mAllInstructors = mInstructorDAO.getAllInstructors();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllInstructors;
    }

    /**
     * Insert Instructors in db
     *
     * @param instructor
     */
    public long insert(Instructor instructor) {
        return mInstructorDAO.insert(instructor);
    }

    /**
     * Update Instructors in db
     *
     * @param instructor
     */
    public int update(Instructor instructor) {
        return mInstructorDAO.update(instructor);
    }

    /**
     * Delete Instructors in db
     *
     * @param instructor
     */
    public int delete(Instructor instructor) {
        return mInstructorDAO.delete(instructor);
    }

    public long insert(User user) {
        return mUserDAO.insert(user);
    }

    public void update(User user) {
        databaseExecutor.execute(() -> {
            mUserDAO.update(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        databaseExecutor.execute(() -> {
            mUserDAO.delete(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public LiveData<List<Course>> getTermCourses(int termID, int userId) {
        return mCourseDAO.getTermCourses(termID, userId);
    }

    public LiveData<List<Assessment>> getAssessmentsForCourse(int courseId, int userId) {
        return mAssessmentDAO.getAssessmentsForCourse(courseId, userId);
    }

    public LiveData<List<CourseR>> getUserCoursesWithTerm(int userId) {
        return mCourseDAO.getUserCoursesWithTerm(userId);
    }

    public LiveData<List<AssessmentR>> getUserAssessmentsWithCourse(int userId) {
        return mAssessmentDAO.getUserAssessmentsWithCourse(userId);
    }

    public LiveData<List<InstructorR>> getUserInstructorsWithCourse(int userId) {
        return mInstructorDAO.getUserInstructorsWithCourse(userId);
    }
}
