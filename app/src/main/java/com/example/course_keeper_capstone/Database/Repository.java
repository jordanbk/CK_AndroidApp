package com.example.course_keeper_capstone.Database;

import android.app.Application;

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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private UserDAO mUserDAO;
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private InstructorDAO mInstructorDAO;
    private List<User> mAllUsers;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<Instructor> mAllInstructors;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);

        mUserDAO = db.userDAO();
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mInstructorDAO = db.instructorDAO();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidAccount(String username, final String password)
    {

        User userAccount = mUserDAO.getUser(username, password);
        return userAccount.getPassword().equals(password);
    }

/*    public User getUser(){
        databaseExecutor.execute(()->{
            mUser = mUserDAO.getUser(String email, String password);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mUser;
    }*/

    public List<User> getAllUsers(){
        databaseExecutor.execute(()->{
            mAllUsers = mUserDAO.getAllUsers();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllUsers;
    }

    /**
     *Get All Terms
     */
    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    /**
     * Insert Terms in db
     * @param term
     */
    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Update Terms in db
     * @param term
     */
    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Delete Terms in db
     * @param term
     */
    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Get all Courses
     * @return
     */
    public List<Course> getAllCourses(){
        databaseExecutor.execute(()->{
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
     * @param course
     */
    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Update Courses in db
     * @param course
     */
    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Delete Courses in db
     * @param course
     */
    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
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
     * @param assessment
     */
    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Update Assessments in db
     * @param assessment
     */
    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Delete Assessments in db
     * @param assessment
     */
    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public List<Instructor> getAllInstructors(){
        databaseExecutor.execute(()->{
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
     * @param instructor
     */
    public void insert(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.insert(instructor);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Update Instructors in db
     * @param instructor
     */
    public void update(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.update(instructor);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Delete Instructors in db
     * @param instructor
     */
    public void delete(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.delete(instructor);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insert(User user) {
        databaseExecutor.execute(()->{
            mUserDAO.insert(user);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(User user){
        databaseExecutor.execute(()->{
            mUserDAO.update(user);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(User user){
        databaseExecutor.execute(()->{
            mUserDAO.delete(user);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}
