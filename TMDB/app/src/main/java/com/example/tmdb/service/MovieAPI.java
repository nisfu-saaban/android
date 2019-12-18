package com.example.tmdb.service;

import com.example.tmdb.model.MovieResponse;
import com.example.tmdb.model.ResultsItem;

import java.util.List;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPI {
    static final String URL_BASE = "http://api.themoviedb.org/3/";
    String IMG_URL = "http://image.tmdb.org/t/p/w185//";

    @GET("movie/popular?api_key=f086569b811ff36c3cbb5c71cda44a31")
    Call<MovieResponse> getPopularity();
}
