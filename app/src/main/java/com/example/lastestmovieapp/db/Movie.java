package com.example.lastestmovieapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "movie_name")
    public String movieName;

    @ColumnInfo(name = "movie_desc")
    public String movieDesc;

    @ColumnInfo(name = "poster_path")
    public String posterPath;

    @ColumnInfo(name = "youtube_id")
    public String youtubeId;
}