package com.example.tmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.tmdb.model.ResultsItem;
import com.example.tmdb.service.MovieServices;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListener {
    RecyclerView rvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MovieServices().getMovie("",this);
    }


    @Override
    public void onSuccess(List<ResultsItem> resultsItem) {
        rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(new MainAdapter(resultsItem));
    }

    @Override
    public void onFailure(String msg) {
        Log.i("ISI",msg);
    }
}
