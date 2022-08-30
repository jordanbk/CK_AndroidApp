package com.example.course_keeper_capstone.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.UI.reports.models.AssessmentR;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Assessment assessment);

    @Update
    int update(Assessment assessment);

    @Delete
    int delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM assessments where userID = :userID")
    LiveData<List<Assessment>> getUserAssessments(int userID);

    @Query("SELECT * FROM assessments where courseID_FK = :courseId and userID = :userId")
    LiveData<List<Assessment>> getAssessmentsForCourse(int courseId, int userId);

    @Query("SELECT a.assessmentTitle,a.assessmentStartDate,a.assessmentEndDate,c.courseTitle FROM assessments as a,courses as c where a.courseID_FK = c.courseID and a.userID = :userId")
    LiveData<List<AssessmentR>> getUserAssessmentsWithCourse(int userId);


}
