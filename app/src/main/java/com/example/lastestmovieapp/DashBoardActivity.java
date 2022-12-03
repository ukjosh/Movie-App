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
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    public List<DashItems> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Movies");


        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.tvHeaderUsername);
        CircleImageView navUserImage = headerView.findViewById(R.id.tvHeaderIcon);
//        Picasso.get().load(Utils.currentUser.getImageUrl()).into(navUserImage);
//        navUsername.setText(Utils.currentUser.getName());

        drawerLayout = findViewById(R.id.my_drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setHasFixedSize(true);

//                    MovieAdapter movieAdapter = new MovieAdapter(this,data.getResults());

        generateRandomColor();


        itemsList = new ArrayList<>();
        itemsList.add(new DashItems("Action",28,R.drawable.action_ic,generateRandomColor()));
        itemsList.add(new DashItems("Adventure",12,R.drawable.adventure_ic,generateRandomColor()));
        itemsList.add(new DashItems("Animation",16,R.drawable.animation_ic,generateRandomColor()));
        itemsList.add(new DashItems("Comedy",35,R.drawable.comedy_ic,generateRandomColor()));
        itemsList.add(new DashItems("Drama",18,R.drawable.drama_ic,generateRandomColor()));
        itemsList.add(new DashItems("Fantasy",14,R.drawable.fantasy_ic,generateRandomColor()));
        itemsList.add(new DashItems("History",36,R.drawable.history_ic,generateRandomColor()));
        itemsList.add(new DashItems("Horror",27,R.drawable.horror_ic,generateRandomColor()));
        itemsList.add(new DashItems("Musical",10402,R.drawable.musical_ic,generateRandomColor()));
        itemsList.add(new DashItems("Mystery",9648,R.drawable.mystery_ic,generateRandomColor()));
        itemsList.add(new DashItems("Romance",10749,R.drawable.romance_ic,generateRandomColor()));
        itemsList.add(new DashItems("Sci-Fi",878,R.drawable.sciencefiction_ic,generateRandomColor()));
        itemsList.add(new DashItems("Thriller",3,R.drawable.thriller_ic,generateRandomColor()));
        itemsList.add(new DashItems("War",53,R.drawable.war_ic,generateRandomColor()));
        itemsList.add(new DashItems("Western",37,R.drawable.western_ic,generateRandomColor()));
        DashboardAdapter dashboardAdapter = new DashboardAdapter(this,itemsList);
        recyclerView.setAdapter(dashboardAdapter);

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        String url = "https://api.themoviedb.org/3/discover/movie?api_key=6ed2279f7b98c9369069fe4760ac0e1f";
//        StringRequest
//                stringRequest
//                = new StringRequest(
//                Request.Method.GET,
//                url,
//                response -> {
//                    Gson gson = new GsonBuilder().create();
//                    DataModel  data = gson.fromJson(response,DataModel.class);
//
//                    recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//                    recyclerView.setHasFixedSize(true);
//
//                    MovieAdapter movieAdapter = new MovieAdapter(this,data.getResults());
//                    recyclerView.setAdapter(movieAdapter);
//
//
//                },
//                error -> {
//
//                });
//        requestQueue.add(stringRequest);
    }

    private Integer generateRandomColor()  {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
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