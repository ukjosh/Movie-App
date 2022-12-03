package com.example.lastestmovieapp;

import static com.example.lastestmovieapp.Utils.searchId;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchMoviesActivity extends AppCompatActivity {

    RecyclerView recyclerView, recyclerViewBtn;

    List<ButtonsModel> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Movies");


        recyclerView = findViewById(R.id.recyclerSearch);
        recyclerViewBtn = findViewById(R.id.btnRecycler);

        recyclerViewBtn.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



        itemList.add(new ButtonsModel("Action",28));
        itemList.add(new ButtonsModel("Adventure",12));
        itemList.add(new ButtonsModel("Animation",16));
        itemList.add(new ButtonsModel("Comedy",35));
        itemList.add(new ButtonsModel("Drama",18));
        itemList.add(new ButtonsModel("Fantasy",14));
        itemList.add(new ButtonsModel("History",36));
        itemList.add(new ButtonsModel("Horror",27));
        itemList.add(new ButtonsModel("Musical",10402));
        itemList.add(new ButtonsModel("Mystery",9648));
        itemList.add(new ButtonsModel("Romance",10749));
        itemList.add(new ButtonsModel("Sci-Fi",878));
        itemList.add(new ButtonsModel("Thriller",3));
        itemList.add(new ButtonsModel("War",53));
        itemList.add(new ButtonsModel("Western",37));

        BtnAdapter btnAdapter = new BtnAdapter(this,itemList,this::refreshRecyclerView);

        recyclerViewBtn.setAdapter(btnAdapter);

//        String searchId = getIntent().getExtras().getString("searchId");

        refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        Log.e("search", "onCreate: "+ searchId);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=6ed2279f7b98c9369069fe4760ac0e1f&with_genres=" + searchId;
        StringRequest
                stringRequest
                = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Gson gson = new GsonBuilder().create();
                    DataModel data = gson.fromJson(response, DataModel.class);

                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    recyclerView.setHasFixedSize(true);

                    MovieAdapter movieAdapter = new MovieAdapter(this, data.getResults());
                    recyclerView.setAdapter(movieAdapter);


                },
                error -> {

                });
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}