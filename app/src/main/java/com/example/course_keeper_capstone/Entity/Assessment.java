package com.example.course_keeper_capstone.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments", foreignKeys = {@ForeignKey(entity = Course.class, parentColumns = {"courseID"}, childColumns = "courseID_FK", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "courseID_FK")})
public class Assessment implements Parcelable {
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
    @ColumnInfo(name = "userID")
    private int userID;


    public Assessment(int assessmentID, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, int courseID_FK, int userID) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.courseID_FK = courseID_FK;
        this.userID = userID;
    }

    @Ignore
    public Assessment(String assessmentTitle, String assessmentStartDate, String assessmentEndDate, int courseID_FK, int userID) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.courseID_FK = courseID_FK;
        this.userID = userID;
    }

    @Ignore
    public Assessment() {

    }


    protected Assessment(Parcel in) {
        assessmentID = in.readInt();
        assessmentTitle = in.readString();
        assessmentStartDate = in.readString();
        assessmentEndDate = in.readString();
        courseID_FK = in.readInt();
        userID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(assessmentID);
        dest.writeString(assessmentTitle);
        dest.writeString(assessmentStartDate);
        dest.writeString(assessmentEndDate);
        dest.writeInt(courseID_FK);
        dest.writeInt(userID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Assessment> CREATOR = new Creator<Assessment>() {
        @Override
        public Assessment createFromParcel(Parcel in) {
            return new Assessment(in);
        }

        @Override
        public Assessment[] newArray(int size) {
            return new Assessment[size];
        }
    };

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


    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "AssessmentR{" +
                "assessmentID=" + assessmentID +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", assessmentStartDate='" + assessmentStartDate + '\'' +
                ", assessmentEndDate='" + assessmentEndDate + '\'' +
                ", courseID_FK=" + courseID_FK +
                ", userID=" + userID +
                '}';
    }
}
