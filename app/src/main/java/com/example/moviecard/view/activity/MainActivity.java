package com.example.moviecard.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.moviecard.R;
import com.example.moviecard.adapter.MoviesAdapter;
import com.example.moviecard.databinding.ActivityMainBinding;
import com.example.moviecard.model.Result;
import com.example.moviecard.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel mainActivityViewModel;
    NavController navigation;
//    private PagedList<Result> resultArray;
//
//    private MoviesAdapter moviesAdapter;
//
//    private NavController navController;
    BottomNavigationView bottomNavigationView;
    public static String RESULT_MOVIE = "result_movie";
    private static int RESULT = 1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(activityMainBinding.getRoot());

        mainActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);


        navigation = Navigation.findNavController(this, R.id.fragment_nav);
        bottomNavigationView = activityMainBinding.bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        navigation.navigate(R.id.homeFragment);
                        break;
                    case R.id.navigation_favorite:
                        navigation.navigate(R.id.favoriteFragment);
                        break;

                }
                return false;
            }

        });
    }


    //    private void getMovies(){
////        mainActivityViewModel.getPopularMovie().observe(this, new Observer<ArrayList<Result>>() {
////            @Override
////            public void onChanged(ArrayList<Result> resultArrayList) {
////                resultArray = resultArrayList;
////                fillRecycleView();
////            }
////        });
//        mainActivityViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Result>>() {
//            @Override
//            public void onChanged(PagedList<Result> results) {
//                resultArray = results;
//                fillRecycleView();
//            }
//        });
//        swipeRefreshLayout.setRefreshing(false);
//
//    }
//    private void fillRecycleView(){
//        //recyclerView = activityMainBinding.recyclerView;
//        moviesAdapter = new MoviesAdapter(getApplicationContext());
//        moviesAdapter.submitList(resultArray);
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//
//        }
//        else {
//            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
//        }
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(moviesAdapter);
//        moviesAdapter.setOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
//            @Override
//            public void onClicker(Result result) {
//                Intent intent = new Intent(getApplicationContext(), MovieItemFragment.class);
//                intent.putExtra(RESULT_MOVIE,result);
//                startActivity(intent);
//            }
//        });
//    }
}