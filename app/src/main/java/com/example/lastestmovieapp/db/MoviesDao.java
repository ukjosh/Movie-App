package com.example.lastestmovieapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE uid IN (:userIds)")
    List<Movie> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(Movie... movies);

    @Delete
    void delete(Movie movie);
}
