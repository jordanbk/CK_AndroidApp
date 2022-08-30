package com.example.course_keeper_capstone.UI.reports.models;

import androidx.room.Ignore;

public class AssessmentR {
    private String assessmentTitle;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private String courseTitle;

    public AssessmentR(String assessmentTitle, String assessmentStartDate, String assessmentEndDate, String courseTitle) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.courseTitle = courseTitle;
    }

    @Ignore
    public AssessmentR() {
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

    public String getCourseTitle() {
        return courseTitle;
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

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
