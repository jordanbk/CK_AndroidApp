package com.example.course_keeper_capstone.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    @ColumnInfo(name = "termName")
    private String termName;
    @ColumnInfo(name = "termStart")
    private String termStart;
    @ColumnInfo(name = "termEnd")
    private String termEnd;
    @ColumnInfo(name = "userID")
    private int userID;

    public Term(int termID, String termName, String termStart, String termEnd, int userID) {
        this.termID = termID;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
        this.userID = userID;
    }

    public Term(String termName, String termStart, String termEnd, int userID) {
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
        this.userID = userID;
    }

    public Term(String termName, String termStart, String termEnd) {
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public Term() {
    }

    //Getters
    public int getTermID() {
        return termID;
    }

    public String getTermName() {
        return termName;
    }

    public String getTermStart() {
        return termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public int getUserID() {
        return userID;
    }

    //Setters
    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termID=" + termID +
                ", termName='" + termName + '\'' +
                ", termStart='" + termStart + '\'' +
                ", termEnd='" + termEnd + '\'' +
                ", userID=" + userID +
                '}';
    }
}
