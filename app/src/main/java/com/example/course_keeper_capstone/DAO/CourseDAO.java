package com.example.course_keeper_capstone.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_keeper_capstone.Entity.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM courses WHERE termID_FK =:termID")
    LiveData<List<Course>> getTermCourses(int termID);

    @Query("SELECT * FROM courses where termID_FK =:termID ORDER BY courseID DESC LIMIT 1")
    Course getSelectedCourse(int termID);
}
