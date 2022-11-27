package com.example.lastestmovieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Movies");


        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.tvHeaderUsername);
        CircleImageView navUserImage = headerView.findViewById(R.id.tvHeaderIcon);
        Picasso.get().load(Utils.currentUser.getImageUrl()).into(navUserImage);
        navUsername.setText(Utils.currentUser.getName());

        drawerLayout = findViewById(R.id.my_drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


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



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_history: {
                startActivity(new Intent(this,HistoryActivity.class));
                break;
            }

            case R.id.nav_logout: {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                finish();
                finishAffinity();
                startActivity(new Intent(this,LoginActivity.class));
                overridePendingTransition(0,0);
                break;
            }

            case R.id.nav_quit: {
                finishAndRemoveTask();
                finishAffinity();
                System.exit(0);
                break;
            }
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}