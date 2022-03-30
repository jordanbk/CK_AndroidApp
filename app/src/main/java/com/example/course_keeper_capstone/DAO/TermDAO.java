package com.example.course_keeper_capstone.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.course_keeper_capstone.Entity.Term;
import com.example.course_keeper_capstone.Entity.User;

import java.util.List;
import java.util.Map;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    List<Term> getAllTerms();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Ignore
    @Query("SELECT * FROM terms JOIN users ON terms.userID = users.Id WHERE terms.userID = users.id AND users.id IN (:userID)")
    List<Term> getUserTerms(int userID);

}
