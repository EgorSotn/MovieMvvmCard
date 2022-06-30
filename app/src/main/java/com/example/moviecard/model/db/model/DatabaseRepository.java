package com.example.moviecard.model.db.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.moviecard.model.db.FavoriteDao;

import com.example.moviecard.model.db.UserDatabase;
import com.example.moviecard.model.Result;

import java.util.List;

public class DatabaseRepository {
    private FavoriteDao getFavoriteDao;
    public DatabaseRepository(Application application){
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        getFavoriteDao = userDatabase.getFavoriteDao();
    }

    public LiveData<Result> getFavoriteMovie(Integer id){
      return getFavoriteDao.getFavoriteMovie(id);
    }
    public LiveData<List<Result>> getAllFavorite(){
        return getFavoriteDao.getAllFavoriteMovie();
    }

    public void deleteFavorite(Result result){
        new DeleteFavoriteAsyncTask(getFavoriteDao).execute(result);
    }
    public void addFavorite(Result result){
        new AddFavoriteAsyncTask(getFavoriteDao).execute(result);
    }

    class DeleteFavoriteAsyncTask extends AsyncTask<Result,Void,Void>{
        private FavoriteDao favoriteDao;

        public DeleteFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            favoriteDao.delete(results[0]);
            return null;
        }
    }
    class AddFavoriteAsyncTask extends AsyncTask<Result,Void,Void>{
        private FavoriteDao favoriteDao;

        public AddFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            favoriteDao.insert(results[0]);
            return null;
        }
    }
}
