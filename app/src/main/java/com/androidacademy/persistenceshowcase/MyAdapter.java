package com.androidacademy.persistenceshowcase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.persistenceshowcase.Database.AppDatabase;
import com.androidacademy.persistenceshowcase.Models.Film;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Film> mDataset;
    private Context context;

    public MyAdapter(List<Film> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Film film = mDataset.get(position);
        holder.title.setText(film.getTitle());
        holder.description.setText(film.getDescription().replaceAll("[\n]", ""));
        Glide.with(context).load(film.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, description;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_item_image);
            title = view.findViewById(R.id.tv_item_title);
            description = view.findViewById(R.id.tv_item_description);

        }
    }
}
