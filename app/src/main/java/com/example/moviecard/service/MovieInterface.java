package com.example.moviecard.service;

import com.example.moviecard.model.MovieApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInterface {
    @GET("/3/movie/popular")
    Call<MovieApiResponse> getMovie(@Query("api_key") String api_key);

    @GET("/3/movie/popular")
    Call<MovieApiResponse> getPopularMoviesWithPge(@Query("api_key") String api_key, @Query("page") long page);
}
