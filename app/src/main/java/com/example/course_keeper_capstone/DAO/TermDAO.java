package com.example.course_keeper_capstone.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_keeper_capstone.Entity.Term;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    List<Term> getAllTerms();
}
