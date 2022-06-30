package com.example.moviecard.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecard.model.Result;

public class FragmentFavoriteViewModel extends ViewModel {

    private static MutableLiveData<Result> resultMutableLiveData;
    public synchronized MutableLiveData<Result> getResultMutableLiveData(){
        if(resultMutableLiveData == null){
            resultMutableLiveData = new MutableLiveData<>();
            Log.d("TAG", "Firs mutable");
        }

        return resultMutableLiveData;
    }

}