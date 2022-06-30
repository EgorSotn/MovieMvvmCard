package com.example.moviecard.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;


import com.example.moviecard.R;
import com.example.moviecard.service.MovieInterface;
import com.example.moviecard.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private ArrayList<Result> results = new ArrayList<>();
    private MutableLiveData<List<Result>> mutableLiveData =
            new MutableLiveData<>();
    private Application application;

    public Repository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Result>> getMutableLiveData() {

        MovieInterface movieApiService = RetrofitInstance.getInstance();

        Call<MovieApiResponse> call = movieApiService
                .getMovie(application.getApplicationContext()
                        .getString(R.string.api_key));

        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call,
                                   Response<MovieApiResponse> response) {

                MovieApiResponse movieApiResponse =
                        response.body();

                if (movieApiResponse != null &&
                        movieApiResponse.getResults() != null) {

                    results = (ArrayList<Result>) movieApiResponse
                            .getResults();
                    mutableLiveData.setValue(results);

                }

            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}