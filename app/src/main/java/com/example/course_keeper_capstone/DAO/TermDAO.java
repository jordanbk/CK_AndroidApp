package com.example.course_keeper_capstone.DAO;

import androidx.lifecycle.LiveData;
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
    long insert(Term term);

    @Update
    int update(Term term);

    @Delete
    int delete(Term term);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    LiveData<List<Term>> getAllTerms();

    //@Ignore
    @Query("SELECT * FROM terms where userID =:userID")
    LiveData<List<Term>> getUserTerms(int userID);

    @Query("SELECT * FROM terms WHERE terms.termName LIKE :searchQuery")
    LiveData<List<Term>> searchTerms(String searchQuery);

}
