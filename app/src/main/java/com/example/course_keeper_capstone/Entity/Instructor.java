package com.example.course_keeper_capstone.Entity;

import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors", foreignKeys = {@ForeignKey(entity = Course.class, parentColumns = {"courseID"}, childColumns = "courseID", onDelete = CASCADE)},
        indices = {@Index(value = "courseID")})
public class Instructor implements Parcelable {
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
    @ColumnInfo
    private int userID;


    public Instructor(int instructorID, String instructorName, String instructorEmail, String instructorPhone, int courseID, int userID) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.courseID = courseID;
        this.userID = userID;
    }

    @Ignore
    public Instructor(String instructorName, String instructorEmail, String instructorPhone, int courseID, int userID) {
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.courseID = courseID;
        this.userID = userID;
    }

    @Ignore
    public Instructor() {
    }


    protected Instructor(Parcel in) {
        instructorID = in.readInt();
        instructorName = in.readString();
        instructorEmail = in.readString();
        instructorPhone = in.readString();
        courseID = in.readInt();
        userID = in.readInt();
    }

    public static final Creator<Instructor> CREATOR = new Creator<Instructor>() {
        @Override
        public Instructor createFromParcel(Parcel in) {
            return new Instructor(in);
        }

        @Override
        public Instructor[] newArray(int size) {
            return new Instructor[size];
        }
    };

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

    public int getUserID() {
        return userID;
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

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "InstructorR{" +
                "instructorID=" + instructorID +
                ", instructorName='" + instructorName + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", courseID=" + courseID +
                ", userID=" + userID +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(instructorID);
        dest.writeString(instructorName);
        dest.writeString(instructorEmail);
        dest.writeString(instructorPhone);
        dest.writeInt(courseID);
        dest.writeInt(userID);
    }
}
