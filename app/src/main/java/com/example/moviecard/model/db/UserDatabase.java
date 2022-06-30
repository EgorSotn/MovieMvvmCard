package com.example.moviecard.model.db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviecard.model.Result;

@Database(entities = {Result.class},version = 3, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;

    public abstract FavoriteDao getFavoriteDao();

    public static UserDatabase getInstance(Application application){
        if(instance == null){
            instance = Room.databaseBuilder(application.getApplicationContext(), UserDatabase.class, "mydb")
                    .fallbackToDestructiveMigration()
                    .build();
        }
       return instance;
    }
}
