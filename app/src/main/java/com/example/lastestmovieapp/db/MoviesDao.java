package com.example.lastestmovieapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE uid IN (:userIds)")
    List<Movie> loadAllByIds(int[] userIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Movie... movies);

    @Query("DELETE FROM movie WHERE uid = :userId")
    void deleteByUserId(long userId);

    @Delete
    void delete(Movie movie);
}
