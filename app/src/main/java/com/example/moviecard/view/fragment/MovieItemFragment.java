package com.example.moviecard.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.moviecard.R;
import com.example.moviecard.databinding.MovieItemBinding;
import com.example.moviecard.model.Result;
import com.example.moviecard.view.activity.MainActivity;
import com.example.moviecard.viewmodel.FragmentFavoriteViewModel;
import com.example.moviecard.viewmodel.MainActivityViewModel;

public class MovieItemFragment extends Fragment {
    Result resultMovie;

    private MovieItemBinding movieItemBinding;
    private MainActivityViewModel mainActivityViewModel;
    private FragmentFavoriteViewModel fragmentFavoriteViewModel;
    CheckBox checkBox;
    private MutableLiveData<Result> movieResult;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        movieItemBinding = MovieItemBinding.inflate(inflater, container,false);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        fragmentFavoriteViewModel = new ViewModelProvider(this).get(FragmentFavoriteViewModel.class);
        return movieItemBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

       fragmentFavoriteViewModel.getResultMutableLiveData().observe(this, new Observer<Result>() {
           @Override
           public void onChanged(Result result) {
               if (result == null){
                   Log.d("TAG", "Result is null");
               }
               else {
                   Log.d("TAG", "Result is not null");
                   resultMovie = result;
                   movieItemBinding.setMovieResult(resultMovie);

                   init(movieItemBinding.checkBox);
               }

           }
       });
    }

    //    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.movie_item);
//        movieItemBinding = DataBindingUtil.setContentView(this,R.layout.movie_item);
//
//        init();
//    }
//
    private void init(CheckBox checkBox) {

       checkBox.setChecked(resultMovie.isFavorite());
       checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(!resultMovie.isFavorite()){
                   resultMovie.setFavorite(true);
                   mainActivityViewModel.insertFavoriteMovies(resultMovie);
               }
               else {
                   resultMovie.setFavorite(false);
                   mainActivityViewModel.deleteFavoriteMovies(resultMovie);
               }
           }
       });
    }
}
