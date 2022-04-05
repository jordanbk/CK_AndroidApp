package com.example.course_keeper_capstone.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    @ColumnInfo(name = "assessmentTitle")
    private String assessmentTitle;
    @ColumnInfo(name = "assessmentStartDate")
    private String assessmentStartDate;
    @ColumnInfo(name = "assessmentEndDate")
    private String assessmentEndDate;
    @ColumnInfo(name = "courseID_FK")
    private int courseID_FK;
    @ColumnInfo(name = "assessmentKind")
    private String assessmentKind;
    @ColumnInfo(name = "userID")
    private int userID;

    @Ignore
    public Assessment(int assessmentID, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, int courseID_FK, String assessmentKind, int userID) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.courseID_FK = courseID_FK;
        this.assessmentKind = assessmentKind;
        this.userID = userID;
    }

    public Assessment() {

    }

    public Assessment(String trim, String assessmentStartDate, String assessmentEndDate, String assessmentKind, int courseID_fk) {
    }

    //Getters
    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public int getCourseID_FK() {
        return courseID_FK;
    }

    public String getAssessmentKind() {
        return assessmentKind;
    }

    public int getUserID() {
        return userID;
    }

    //Setters

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public void setCourseID_FK(int courseID_FK) {
        this.courseID_FK = courseID_FK;
    }

    public void setAssessmentKind(String assessmentKind) {
        this.assessmentKind = assessmentKind;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentID=" + assessmentID +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", assessmentStartDate='" + assessmentStartDate + '\'' +
                ", assessmentEndDate='" + assessmentEndDate + '\'' +
                ", courseID_FK=" + courseID_FK +
                ", assessmentKind='" + assessmentKind + '\'' +
                ", userID=" + userID +
                '}';
    }
}
