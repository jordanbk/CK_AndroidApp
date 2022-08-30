package com.example.course_keeper_capstone.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "terms")
public class Term implements Parcelable {
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

    @Ignore
    public Term(String termName, String termStart, String termEnd, int userID) {
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
        this.userID = userID;
    }
    @Ignore
    public Term(String termName, String termStart, String termEnd) {
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }
    @Ignore
    public Term() {
    }

    protected Term(Parcel in) {
        termID = in.readInt();
        termName = in.readString();
        termStart = in.readString();
        termEnd = in.readString();
        userID = in.readInt();
    }

    public static final Creator<Term> CREATOR = new Creator<Term>() {
        @Override
        public Term createFromParcel(Parcel in) {
            return new Term(in);
        }

        @Override
        public Term[] newArray(int size) {
            return new Term[size];
        }
    };

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
        return "TermR{" +
                "termID=" + termID +
                ", termName='" + termName + '\'' +
                ", termStart='" + termStart + '\'' +
                ", termEnd='" + termEnd + '\'' +
                ", userID=" + userID +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(termID);
        parcel.writeString(termName);
        parcel.writeString(termStart);
        parcel.writeString(termEnd);
        parcel.writeInt(userID);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
