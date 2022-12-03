package com.example.lastestmovieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lastestmovieapp.db.AppDatabase;
import com.example.lastestmovieapp.db.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);




        String idFromIntent = getIntent().getStringExtra("id");
        String title1 = getIntent().getStringExtra("title");
        String desc1 = getIntent().getStringExtra("desc");
        String releaseDate = getIntent().getStringExtra("releaseDate");
        String starsRating = getIntent().getStringExtra("starsRating");
        String poster = getIntent().getStringExtra("poster");



        ImageView imageView = findViewById(R.id.imageView);
        Picasso.get().load("https://image.tmdb.org/t/p/w300"+poster).into(imageView);

        TextView title = findViewById(R.id.titleText);
        title.setText(title1);

        TextView desc = findViewById(R.id.descText);
        desc.setText(desc1);

        TextView releaseDateText = findViewById(R.id.releaseText);
        releaseDateText.setText(releaseDate);

        TextView starsRatingText = findViewById(R.id.startRatingText);
        starsRatingText.setText(starsRating);



        findViewById(R.id.btnTapToPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase db = AppDatabase.getDbInstance(MovieDetailsActivity.this);
                Movie movie = new Movie();

                movie.movieName = getIntent().getStringExtra("title");
                movie.posterPath = getIntent().getStringExtra("poster");
                Log.e("poster", "onClick: "+getIntent().getStringExtra("poster"));
                movie.movieDesc = desc1;
                movie.youtubeId = idFromIntent;


                String id = String.valueOf(idFromIntent);
                RequestQueue requestQueue = Volley.newRequestQueue(MovieDetailsActivity.this);
                String url = "https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=6ed2279f7b98c9369069fe4760ac0e1f";
                StringRequest
                        stringRequest
                        = new StringRequest(
                        Request.Method.GET,
                        url,
                        response -> {
                            Gson gson = new GsonBuilder().create();
                            VideoKeyModel  key = gson.fromJson(response,VideoKeyModel.class);

                            String videoId = key.getResults().get(0).getKey();
                            Intent intent = new Intent(MovieDetailsActivity.this,YouTubePlayerActivity.class);
                            Utils.videoId = key.getResults().get(0).getKey();
                            intent.putExtra("videoId",videoId);
                            startActivity(intent);
                        },
                        error -> {
                            Toast.makeText(MovieDetailsActivity.this, "Unable to get response!", Toast.LENGTH_SHORT).show();
                        });
                requestQueue.add(stringRequest);

                db.moviesDao().insertAll(movie);
            }
        });
    }
}