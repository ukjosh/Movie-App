package com.example.lastestmovieapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BtnAdapter extends RecyclerView.Adapter<BtnAdapter.ViewHolder>{


    private final Context context;
    private final List<ButtonsModel> itemsList;
    private final btnClickListeners listeners;


    public BtnAdapter(Context context, List<ButtonsModel> itemsList, btnClickListeners listeners) {
        this.context = context;
        this.itemsList = itemsList;
        this.listeners = listeners;
    }

    @NonNull
    @Override
    public BtnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.btn_item,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BtnAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(itemsList.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.searchId = String.valueOf(itemsList.get(position).id);
                listeners.onItemClickListener();
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleBtn);
        }
    }
}
