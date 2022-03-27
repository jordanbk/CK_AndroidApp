package com.example.course_keeper_capstone.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.course_keeper_capstone.DAO.AssessmentDAO;
import com.example.course_keeper_capstone.DAO.CourseDAO;
import com.example.course_keeper_capstone.DAO.InstructorDAO;
import com.example.course_keeper_capstone.DAO.TermDAO;
import com.example.course_keeper_capstone.DAO.UserDAO;
import com.example.course_keeper_capstone.Entity.Assessment;
import com.example.course_keeper_capstone.Entity.Course;
import com.example.course_keeper_capstone.Entity.Instructor;
import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.Entity.User;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Term.class, Course.class, Assessment.class, Instructor.class}, version = 12)
public abstract class DatabaseBuilder extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public abstract UserDAO userDAO();
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract InstructorDAO instructorDAO();
    private static volatile DatabaseBuilder INSTANCE;
    //public abstract UserDAO getUser();

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseBuilder.class,
                            "newDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static DatabaseBuilder.Callback populateDatabaseCallback = new DatabaseBuilder.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                UserDAO userDao = INSTANCE.userDAO();
                TermDAO termDao = INSTANCE.termDAO();
                CourseDAO courseDao = INSTANCE.courseDAO();
                AssessmentDAO assessmentDao = INSTANCE.assessmentDAO();
                InstructorDAO instructorDao = INSTANCE.instructorDAO();

            });
        }

    };
}
