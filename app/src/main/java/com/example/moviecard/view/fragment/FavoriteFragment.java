package com.example.moviecard.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecard.R;
import com.example.moviecard.adapter.FavoriteAdapter;
import com.example.moviecard.adapter.MoviesAdapter;
import com.example.moviecard.databinding.FragmentFavoriteBinding;
import com.example.moviecard.model.Result;
import com.example.moviecard.viewmodel.FragmentFavoriteViewModel;
import com.example.moviecard.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    FragmentFavoriteBinding fragmentFavoriteBinding;
    RecyclerView recyclerView;
    ArrayList<Result> resultArrayList;
    FavoriteAdapter favoriteAdapter;
    FragmentFavoriteViewModel fragmentFavoriteViewModel;
    MainActivityViewModel mainActivityViewModel;
    Result resultMovie;
    NavController navController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater,container,false);
        fragmentFavoriteViewModel = new ViewModelProvider(this).get(FragmentFavoriteViewModel.class);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        return  fragmentFavoriteBinding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        navController = Navigation.findNavController(getActivity(),R.id.fragment_nav);
        Log.d("TAG", "Create Favorite");
        getFavorite();
    }
    private void getFavorite(){
        mainActivityViewModel.getAllRoomFavoriteMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                resultArrayList = (ArrayList<Result>) results;
                initAdapter();
            }
        });
    }

    private void initAdapter() {
        favoriteAdapter = new FavoriteAdapter(getContext());
        favoriteAdapter.setArrayList(resultArrayList);
        recyclerView = fragmentFavoriteBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(favoriteAdapter);

       favoriteAdapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
           @Override
           public void onClicker(Result result) {
               fragmentFavoriteViewModel.getResultMutableLiveData().postValue(result);
               navController.navigate(R.id.movieItemActivity);

           }
       });
       initItemTouchHelper();

    }
    private void initItemTouchHelper(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Result result = resultArrayList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteFavoriteMovies(result);
            }
        }).attachToRecyclerView(recyclerView);
    }

}