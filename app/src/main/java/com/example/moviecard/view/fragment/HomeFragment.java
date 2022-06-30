package com.example.moviecard.view.fragment;


import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviecard.R;
import com.example.moviecard.adapter.MoviesAdapter;
import com.example.moviecard.databinding.FragmentHomeBinding;
import com.example.moviecard.model.Result;

import com.example.moviecard.viewmodel.FragmentFavoriteViewModel;
import com.example.moviecard.viewmodel.MainActivityViewModel;




public class HomeFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentHomeBinding fragmentHomeBinding;
    private MoviesAdapter moviesAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private PagedList<Result> resultArray;
    private RecyclerView recyclerView;
    private FragmentFavoriteViewModel fragmentFavoriteViewModel;
    NavController navigation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        fragmentFavoriteViewModel = new ViewModelProvider(this).get(FragmentFavoriteViewModel.class);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        return fragmentHomeBinding.getRoot();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        swipeRefreshLayout = fragmentHomeBinding.swiper;
        recyclerView = fragmentHomeBinding.recyclerView;
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.purple_200));
        navigation = Navigation.findNavController(getActivity(),R.id.fragment_nav);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovies();
            }
        });
        getMovies();
    }

    private void getMovies(){
//        mainActivityViewModel.getPopularMovie().observe(this, new Observer<ArrayList<Result>>() {
//            @Override
//            public void onChanged(ArrayList<Result> resultArrayList) {
//                resultArray = resultArrayList;
//                fillRecycleView();
//            }
//        });
        mainActivityViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> results) {
                resultArray = results;
                for(Result result:results){
                    Log.d("TAG", result.getTitle());
                }

                fillRecycleView();
            }
        });
        swipeRefreshLayout.setRefreshing(false);

    }

    private void fillRecycleView(){
        recyclerView = fragmentHomeBinding.recyclerView;
        moviesAdapter = new MoviesAdapter(getContext());
        moviesAdapter.submitList(resultArray);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);

        moviesAdapter.setOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onClicker(Result resultAdapter) {

                mainActivityViewModel.getRoomFavorite(resultAdapter.getId()).observe(getViewLifecycleOwner(), new Observer<Result>() {
                    @Override
                    public void onChanged(Result result) {
                        if (result != null){
                            fragmentFavoriteViewModel.getResultMutableLiveData().postValue(result);
                            Log.d("TAG", "room object");
                        }
                        else {
                            fragmentFavoriteViewModel.getResultMutableLiveData().postValue(resultAdapter);
                            Log.d("TAG", resultAdapter.getId().toString()+" Adapter");
                        }
                        Toast.makeText(getContext(), "!!!", Toast.LENGTH_SHORT).show();
                        navigation.navigate(R.id.movieItemActivity);
                    }

                });
//                fragmentFavoriteViewModel.getResultMutableLiveData().postValue(result);


                //338953
               // Log.d("TAG", "Retrofit id: " + resultAdapter.getId().toString());

//                Intent intent = new Intent(getApplicationContext(), MovieItemFragment.class);
//                intent.putExtra(RESULT_MOVIE,result);
//                startActivity(intent);
            }
        });

    }
}