package com.example.course_keeper_capstone.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    @ColumnInfo(name = "courseTitle")
    private String courseTitle;
    @ColumnInfo(name = "courseStatus")
    private String courseStatus;
    @ColumnInfo(name = "courseStart")
    private String courseStart;
    @ColumnInfo(name = "courseEnd")
    private String courseEnd;
    @ColumnInfo(name = "courseNotes")
    private String courseNotes;
    @ColumnInfo(name = "courseAlert")
    private boolean courseAlert;
    @ColumnInfo(name = "termID_FK")
    private int termID_FK;
    @ColumnInfo (name = "userID")
    private int userID;

    @Ignore
    public Course(int courseID, String courseTitle, String courseStatus, String courseStart, String courseEnd, String courseNotes, boolean courseAlert, int termID_FK, int userID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseStatus = courseStatus;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseNotes = courseNotes;
        this.courseAlert = courseAlert;
        this.termID_FK = termID_FK;
        this.userID = userID;
    }

    public Course() {

    }

    public Course(String courseTitle, String courseStart, String courseEnd, String courseStatus, int termID_fk) {
    }


    //Getters

    public int getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public boolean isCourseAlert() { return courseAlert; }

    public int getTermID_FK() {
        return termID_FK;
    }

    public int getUserID() {
        return userID;
    }

    //setters

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public void setCourseAlert(boolean courseAlert) {
        this.courseAlert = courseAlert;
    }

    public void setTermID_FK(int termID_FK) {
        this.termID_FK = termID_FK;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseStart='" + courseStart + '\'' +
                ", courseEnd='" + courseEnd + '\'' +
                ", courseNotes='" + courseNotes + '\'' +
                ", courseAlert=" + courseAlert +
                ", termID_FK=" + termID_FK +
                ", userID=" + userID +
                '}';
    }
}
