package com.example.course_keeper_capstone.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_keeper_capstone.Entity.Instructor;
import com.example.course_keeper_capstone.UI.reports.models.InstructorR;

import java.util.List;

@Dao
public interface InstructorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Instructor instructor);

    @Update
    int update(Instructor instructor);

    @Delete
    int delete(Instructor instructor);

    @Query("SELECT * FROM instructors ORDER BY instructorID ASC")
    LiveData<List<Instructor>> getAllInstructors();


    @Query ("SELECT * FROM instructors WHERE courseID = :courseID ORDER BY instructorName ASC")
    LiveData<List<Instructor>> getAllAssociatedMentors(int courseID);

    @Query("SELECT * FROM instructors where userID = :userID")
    LiveData<List<Instructor>> getUserInstructors(int userID);

    @Query("SELECT i.instructorName,i.instructorEmail,i.instructorPhone,c.courseTitle FROM instructors as i,courses as c where i.courseID = c.courseID and i.userID = :userId")
    LiveData<List<InstructorR>> getUserInstructorsWithCourse(int userId);
}
