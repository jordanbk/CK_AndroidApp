package com.example.course_keeper_capstone.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int instructorID;
    @ColumnInfo
    private String instructorName;
    @ColumnInfo
    private String instructorEmail;
    @ColumnInfo
    private String instructorPhone;
    @ColumnInfo
    private int courseID;

    public Instructor(int instructorID, String instructorName, String instructorEmail, String instructorPhone, int courseID) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.courseID = courseID;
    }

    //Getters


    public int getInstructorID() {
        return instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public int getCourseID() {
        return courseID;
    }

    //Setters

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instructorID=" + instructorID +
                ", instructorName='" + instructorName + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", courseID=" + courseID +
                '}';
    }
}
