package com.example.musicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MuAdapter extends RecyclerView.Adapter<MuAdapter.MuViewHolder> {
List<Music> music;
MuAdapter(List<Music> music){
    this.music = music;
}
    @NonNull
    @Override
    public MuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MuViewHolder holder, int position) {
holder.bind(music.get(position));
    }

    @Override
    public int getItemCount() {
        return music.size();
    }

    public class MuViewHolder extends RecyclerView.ViewHolder{

TextView name,artist;
ImageView imageView;
        public MuViewHolder(@NonNull View itemView) {
            super(itemView);
            artist = itemView.findViewById(R.id.tv_artist_name);
            name = itemView.findViewById(R.id.tv_mu);
            imageView = itemView.findViewById(R.id.iv_art);
        }
        public void bind(Music music){
            name.setText(music.getArtist());
            artist.setText(music.getName());
            imageView.setImageResource(music.getCoverResId());

        }
    }
}
