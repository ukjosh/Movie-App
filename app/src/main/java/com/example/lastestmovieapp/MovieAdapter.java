package com.example.lastestmovieapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.lastestmovieapp.db.AppDatabase;
import com.example.lastestmovieapp.db.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    List<DataModel.Result> resultsList;
    Context context;

    public MovieAdapter(Context context, List<DataModel.Result> results) {
        this.context = context;
        this.resultsList = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_items_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.title.setText(resultsList.get(position).getTitle());
        holder.desc.setText(resultsList.get(position).getOverview());
        Picasso.get().load("https://image.tmdb.org/t/p/w300"+resultsList.get(position).getPosterPath()).into(holder.thumbnailImage);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context,MovieDetailsActivity.class);
            intent.putExtra("id",resultsList.get(position).getId()+"");
            intent.putExtra("title",resultsList.get(position).getTitle());
            intent.putExtra("desc",resultsList.get(position).getOverview());
            intent.putExtra("releaseDate",resultsList.get(position).getReleaseDate());
            intent.putExtra("starsRating",resultsList.get(position).getPopularity());
            intent.putExtra("poster",resultsList.get(position).getPosterPath());
            context.startActivity(intent);

        });

//        Picasso.get().load(resultsList.get(position).getPosterPath()).into(holder.Approvedimg);

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnailImage;
        TextView title, desc;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            thumbnailImage = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.description);
        }
    }

}
