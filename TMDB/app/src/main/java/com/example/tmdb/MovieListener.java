package com.example.tmdb;

import com.example.tmdb.model.ResultsItem;

import java.util.List;

public interface MovieListener {
    public void onSuccess(List<ResultsItem> resultsItem);
    public void onFailure(String msg);
}
