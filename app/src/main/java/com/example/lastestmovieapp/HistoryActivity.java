package com.example.lastestmovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lastestmovieapp.db.AppDatabase;
import com.example.lastestmovieapp.db.Movie;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryListAdapter userListAdapter;

    List<Movie> movieList;

    TextView noDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Objects.requireNonNull(getSupportActionBar()).setTitle("History");

        noDataFound = findViewById(R.id.noDataFound);



        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        loadUserList();

        if (movieList.size() == 0){
            noDataFound.setVisibility(View.VISIBLE);
        }else {
            noDataFound.setVisibility(View.GONE);
        }





    }

    private void loadUserList() {


        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        movieList = db.moviesDao().getAll();

        Log.e("movieList", "loadUserList: "+movieList.size());
        Log.e("movieList", "loadUserList: "+movieList.get(0).posterPath);

        Collections.reverse(movieList);
        userListAdapter = new HistoryListAdapter(HistoryActivity.this,movieList,true);
        recyclerView.setAdapter(userListAdapter);
    }
}