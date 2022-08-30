package com.example.course_keeper_capstone.UI.reports.models;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

public class TermR {
    private int termID;
    private String termName;
    private String termStart;
    private String termEnd;

    public TermR(int termID, String termName, String termStart, String termEnd) {
        this.termID = termID;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    @Ignore
    public TermR() {
    }

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

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
