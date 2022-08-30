package com.example.course_keeper_capstone.UI.reports.models;

import androidx.room.Ignore;

public class CourseR {
    private String courseTitle;
    private String courseStatus;
    private String courseStart;
    private String courseEnd;
    private String courseNotes;
    private String termName;

    public CourseR(String courseTitle, String courseStatus, String courseStart, String courseEnd, String courseNotes, String termName) {
        this.courseTitle = courseTitle;
        this.courseStatus = courseStatus;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseNotes = courseNotes;
        this.termName = termName;
    }

    @Ignore
    public CourseR(){}

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

    public String getTermName() {
        return termName;
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

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
