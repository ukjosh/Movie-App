package com.example.lastestmovieapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private final Context context;
    private final List<DashItems> itemsList;

    public DashboardAdapter(Context context, List<DashItems> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dash_items,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemBg.setBackgroundColor(itemsList.get(position).bgColor);
        holder.itemTitle.setText(itemsList.get(position).title);
//        Picasso.get().load(itemsList.get(position).icon).into(holder.itemImage);
        holder.itemImage.setImageResource(itemsList.get(position).icon);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,SearchMoviesActivity.class);
            Utils.searchId = String.valueOf(itemsList.get(position).searchId);
            intent.putExtra("searchId",itemsList.get(position).searchId);
            Log.e("search", "onBindViewHolder: "+itemsList.get(position).searchId );
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView itemBg;
        ImageView itemImage;
        TextView itemTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemBg = itemView.findViewById(R.id.itemBg);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
        }
    }
}
