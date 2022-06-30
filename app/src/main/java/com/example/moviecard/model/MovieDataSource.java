package com.example.moviecard.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;


import com.example.moviecard.R;
import com.example.moviecard.service.MovieInterface;
import com.example.moviecard.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Result> {

    private MovieInterface movieInterface;
    private Application application;

    public MovieDataSource(MovieInterface movieInterface,
                           Application application) {
        this.movieInterface = movieInterface;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long, Result> callback) {

        movieInterface = RetrofitInstance.getInstance();
        Call<MovieApiResponse> call = movieInterface.getPopularMoviesWithPge(
                application.getApplicationContext().getString(R.string.api_key),
                1
        );

        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call,
                                   Response<MovieApiResponse> response) {

                MovieApiResponse movieApiResponse = response.body();
                ArrayList<Result> results = new ArrayList<>();

                if (movieApiResponse != null &&
                        movieApiResponse.getResults() != null) {

                    results = (ArrayList<Result>) movieApiResponse.getResults();
                    callback.onResult(results, null, (long)2);

                }

            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params,
                           @NonNull LoadCallback<Long, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, Result> callback) {

        movieInterface = RetrofitInstance.getInstance();
        Call<MovieApiResponse> call = movieInterface.getPopularMoviesWithPge(
                application.getApplicationContext().getString(R.string.api_key),
                params.key
        );

        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call,
                                   Response<MovieApiResponse> response) {

                MovieApiResponse movieApiResponse = response.body();
                ArrayList<Result> results = new ArrayList<>();

                if (movieApiResponse != null &&
                        movieApiResponse.getResults() != null) {

                    results = (ArrayList<Result>) movieApiResponse.getResults();
                    callback.onResult(results, params.key + 1);

                }


            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

            }
        });

    }
}