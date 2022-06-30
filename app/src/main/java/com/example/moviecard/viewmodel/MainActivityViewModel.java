package com.example.moviecard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.moviecard.model.db.model.DatabaseRepository;
import com.example.moviecard.model.MovieDataSource;
import com.example.moviecard.model.MovieDataSourceFactory;
import com.example.moviecard.model.Repository;
import com.example.moviecard.model.Result;
import com.example.moviecard.service.MovieInterface;
import com.example.moviecard.service.RetrofitInstance;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivityViewModel extends AndroidViewModel {

    private Repository movieRepository;
    private MutableLiveData<MovieDataSource> movieDataSourceMutableLiveData;
    private Executor executor;
    private LiveData<PagedList<Result>> pagedListLiveData;
    private LiveData<List<Result>> results;
    private DatabaseRepository databaseRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        //Room
        databaseRepository = new DatabaseRepository(application);



        movieRepository = new Repository(application);
        MovieInterface movieApiService = RetrofitInstance.getInstance();

        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(application, movieApiService);
        movieDataSourceMutableLiveData = movieDataSourceFactory.getMutableLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(3)
                .build();

        executor = Executors.newCachedThreadPool();

        pagedListLiveData = new LivePagedListBuilder<Long, Result>(movieDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Result>> getPagedListLiveData() {
        return pagedListLiveData;
    }



    public LiveData<List<Result>> getAllRoomFavoriteMovies(){
        results = databaseRepository.getAllFavorite();
        return results;
    }

    public void insertFavoriteMovies(Result movies){
        databaseRepository.addFavorite(movies);
    }

    public void deleteFavoriteMovies(Result movies){
        databaseRepository.deleteFavorite(movies);
    }

//

    public LiveData<Result> getRoomFavorite(Integer id){
        return databaseRepository.getFavoriteMovie(id);
    }

}