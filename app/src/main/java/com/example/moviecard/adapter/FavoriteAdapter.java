package com.example.moviecard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecard.R;
import com.example.moviecard.databinding.MovieItemListBinding;
import com.example.moviecard.model.Result;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{
    private  Context context;
    private ArrayList<Result> arrayList;
    private OnItemClickListener onItemClickListener;

    public void setArrayList(ArrayList<Result> arrayList) {
        this.arrayList = arrayList;
    }

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemListBinding movieItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.movie_item_list,parent,false);
        return new FavoriteViewHolder(movieItemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.movieItemListBinding.setResult(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder{
        MovieItemListBinding movieItemListBinding;
        public FavoriteViewHolder(@NonNull MovieItemListBinding movieItemListBinding) {
            super(movieItemListBinding.getRoot());
            this.movieItemListBinding = movieItemListBinding;
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int position = getAdapterPosition();
                   if(onItemClickListener!=null && position!=RecyclerView.NO_POSITION){
                       onItemClickListener.onClicker(arrayList.get(position));
                   }

               }
           });
        }
    }

    public interface OnItemClickListener{
        void onClicker(Result result);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
