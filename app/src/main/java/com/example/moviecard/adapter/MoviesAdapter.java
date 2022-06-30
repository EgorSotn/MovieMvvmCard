package com.example.moviecard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviecard.R;
import com.example.moviecard.databinding.MovieItemListBinding;
import com.example.moviecard.model.Result;

import java.util.ArrayList;

public class MoviesAdapter extends PagedListAdapter<Result, MoviesAdapter.MoviesViewHolder> {
//    ArrayList<Result> resultArrayList;
    OnItemClickListener onItemClickListener;
    Context context;
    public MoviesAdapter(Context context){
        super(Result.CALLBACK);
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        MovieItemListBinding movieItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                ,R.layout.movie_item_list, parent, false);
        return new MoviesViewHolder(movieItemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

        Result result = getItem(position);

        holder.movieItemListBinding.setResult(result);
    }

//    @Override
//    public int getItemCount() {
//        return resultArrayList.size();
//    }

    class MoviesViewHolder extends RecyclerView.ViewHolder{
//        ImageView imageView;
//        TextView name_movie, popularity;
        MovieItemListBinding movieItemListBinding;
        public MoviesViewHolder(@NonNull MovieItemListBinding movieItemListBinding) {

            super(movieItemListBinding.getRoot());
            this.movieItemListBinding = movieItemListBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onClicker(getItem(position));

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
