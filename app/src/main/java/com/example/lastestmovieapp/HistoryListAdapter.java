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
import androidx.recyclerview.widget.RecyclerView;

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

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private final Context context;
    private final List<Movie> movieList;

    public HistoryListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_items_view,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(movieList.get(position).movieName);
        holder.desc.setText(movieList.get(position).movieDesc);
        Picasso.get().load(movieList.get(position).posterPath).into(holder.thumbnailImage);

        holder.itemView.setOnClickListener(v -> {
            String id = movieList.get(position).youtubeId;
            RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                        Intent intent = new Intent(context,YouTubePlayerActivity.class);
                        intent.putExtra("videoId",videoId);
                        context.startActivity(intent);
                    },
                    error -> {
                        Toast.makeText(context, "Unable to get response!", Toast.LENGTH_SHORT).show();
                    });
            requestQueue.add(stringRequest);

        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
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
