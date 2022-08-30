package com.example.course_keeper_capstone.UI.reports.models;

import androidx.room.Ignore;

public class InstructorR {
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;
    private String courseTitle;

    public InstructorR(String instructorName, String instructorEmail, String instructorPhone, String courseTitle) {
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.courseTitle = courseTitle;
    }

    @Ignore
    public InstructorR(){

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

    public String getCourseTitle() {
        return courseTitle;
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

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
