package com.example.course_keeper_capstone.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.UI.reports.models.CourseR;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert
    long insert(Course course);

    @Update
    int update(Course course);

    @Delete
    int delete(Course course);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    LiveData<List<Course>> getAllCourses();

/*     @Query("SELECT * FROM courses WHERE termID_FK =:termID")
    LiveData<List<CourseR>> getTermCourses(int termID);

   @Query("SELECT * FROM courses where termID_FK =:termID ORDER BY courseID DESC LIMIT 1")
    CourseR getSelectedCourse(int termID);*/

    //@Ignore
    @Query("SELECT * FROM courses where userID = :userID")
    LiveData<List<Course>> getUserCourses(int userID);

    @Query("SELECT * FROM courses WHERE courses.courseTitle LIKE :searchQuery")
    LiveData<List<Course>> searchCourses(String searchQuery);

    @Query("SELECT * FROM courses where termID_FK = :termID and userID = :userID")
    LiveData<List<Course>> getTermCourses(int termID,int userID);

    @Query("SELECT c.courseTitle,c.courseStatus,c.courseStart,c.courseEnd,c.courseNotes,t.termName FROM courses as c,terms as t where t.termID = c.termID_FK and c.userID = :userId")
    LiveData<List<CourseR>> getUserCoursesWithTerm(int userId);
}
