package com.example.tmdb;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.model.ResultsItem;
import com.example.tmdb.service.MovieAPI;
import com.example.tmdb.service.MovieServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    List<ResultsItem> resultsItems;

    public MainAdapter(List<ResultsItem> resultsItem){
        resultsItems = resultsItem;
    }


    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder holder, int position) {
        ResultsItem resultsItem = resultsItems.get(position);
        holder.tvTitle.setText(resultsItem.getTitle());

                Picasso.get().load(MovieAPI.IMG_URL + resultsItem.getPosterPath())
                        .fit()
                        .into(holder.ivPoster);


    }

    @Override
    public int getItemCount() {
        return resultsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView ivPoster;
        public ViewHolder(View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivPoster = itemView.findViewById(R.id.iv_poster);
        }
    }

}
