package com.example.moviecard.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviecard.model.Result;

import java.util.List;


@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM result")
    public LiveData<List<Result>> getAllFavoriteMovie();



    @Query("SELECT * FROM result WHERE id ==:movie_id")
    public LiveData<Result> getFavoriteMovie(Integer movie_id);

    @Insert
    public void insert(Result favoriteMovie);

    @Delete
    public void delete(Result favoriteMovie);
}
