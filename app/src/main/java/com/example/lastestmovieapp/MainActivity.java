package com.example.lastestmovieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.recyclerView);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=6ed2279f7b98c9369069fe4760ac0e1f";
        StringRequest
                stringRequest
                = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Gson gson = new GsonBuilder().create();
                    DataModel  data = gson.fromJson(response,DataModel.class);

                    recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                    recyclerView.setHasFixedSize(true);

                    MovieAdapter movieAdapter = new MovieAdapter(this,data.getResults());
                    recyclerView.setAdapter(movieAdapter);


                },
                error -> {

                });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
        finishAndRemoveTask();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}