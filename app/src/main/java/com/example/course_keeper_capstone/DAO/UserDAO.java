package com.example.course_keeper_capstone.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.course_keeper_capstone.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users WHERE username = :username and password = :password")
    User getUser(String username, String password);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
