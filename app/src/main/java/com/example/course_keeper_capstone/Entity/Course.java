package com.example.course_keeper_capstone.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses", foreignKeys = {@ForeignKey(entity = Term.class, parentColumns = {"termID"}, childColumns = "termID_FK", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "termID_FK")})
public class Course implements Parcelable {
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
    @ColumnInfo(name = "termID_FK")
    private int termID_FK;
    @ColumnInfo(name = "userID")
    private int userID;


    public Course(int courseID, String courseTitle, String courseStatus, String courseStart, String courseEnd, String courseNotes, int termID_FK, int userID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseStatus = courseStatus;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseNotes = courseNotes;
        this.termID_FK = termID_FK;
        this.userID = userID;
    }

    @Ignore
    public Course() {
    }

    @Ignore
    public Course(String courseTitle, String courseStatus, String courseStart, String courseEnd, String courseNotes, int termID_FK, int userID) {
        this.courseTitle = courseTitle;
        this.courseStatus = courseStatus;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseNotes = courseNotes;
        this.termID_FK = termID_FK;
        this.userID = userID;
    }
    //Getters

    protected Course(Parcel in) {
        courseID = in.readInt();
        courseTitle = in.readString();
        courseStatus = in.readString();
        courseStart = in.readString();
        courseEnd = in.readString();
        courseNotes = in.readString();
        termID_FK = in.readInt();
        userID = in.readInt();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

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

    public void setTermID_FK(int termID_FK) {
        this.termID_FK = termID_FK;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "CourseR{" +
                "courseID=" + courseID +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseStart='" + courseStart + '\'' +
                ", courseEnd='" + courseEnd + '\'' +
                ", courseNotes='" + courseNotes + '\'' +
                ", termID_FK=" + termID_FK +
                ", userID=" + userID +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(courseID);
        parcel.writeString(courseTitle);
        parcel.writeString(courseStatus);
        parcel.writeString(courseStart);
        parcel.writeString(courseEnd);
        parcel.writeString(courseNotes);
        parcel.writeInt(termID_FK);
        parcel.writeInt(userID);
    }
}
