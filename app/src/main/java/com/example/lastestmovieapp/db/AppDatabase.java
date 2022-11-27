package com.example.lastestmovieapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();

    private static AppDatabase Instance;

    public static AppDatabase getDbInstance(Context context){

        if (Instance == null){
            Instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"movies")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return Instance;
    }
}
