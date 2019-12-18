package com.example.tmdb.service;

import android.util.Log;

import com.example.tmdb.MovieListener;
import com.example.tmdb.model.MovieResponse;
import com.example.tmdb.model.ResultsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieServices {
    private Retrofit retrofit;
    public MovieAPI getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(MovieAPI.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MovieAPI.class);
    }

    public void getMovie(String title, final MovieListener movieListener){
        getRetrofit().getPopularity().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null){
                    movieListener.onSuccess(response.body().getResults());

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.i("ISI ERRoR",t.getMessage());
            }
        });
    }
}
