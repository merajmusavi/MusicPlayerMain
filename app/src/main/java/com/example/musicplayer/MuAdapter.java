package com.example.musicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public class MuAdapter extends RecyclerView.Adapter<MuAdapter.MuViewHolder> {
List<Music> musicList;
    private OnItemClick onItemClick;
    private int playingMusicPos = -1;
MuAdapter(List<Music> music,OnItemClick onItemClick){
    this.musicList = music;
    this.onItemClick = onItemClick;
}
    @NonNull
    @Override
    public MuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MuViewHolder holder, int position) {
holder.bind(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MuViewHolder extends RecyclerView.ViewHolder{


TextView name,artist;
ImageView imageView;
LottieAnimationView lottieAnimationView;
        public MuViewHolder(@NonNull View itemView) {
            super(itemView);
            artist = itemView.findViewById(R.id.tv_artist_name);
            name = itemView.findViewById(R.id.tv_mu);
            imageView = itemView.findViewById(R.id.iv_art);
            lottieAnimationView = itemView.findViewById(R.id.animateview);
        }
        public void bind(Music music){
            name.setText(music.getArtist());
            artist.setText(music.getName());
            imageView.setImageResource(music.getCoverResId());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.OnNewMuClick(music,getAdapterPosition());
                }
            });
            if (getAdapterPosition() == playingMusicPos){
lottieAnimationView.setVisibility(View.VISIBLE);

            }else {
                lottieAnimationView.setVisibility(View.GONE);
            }

        }
    }
    public void notifyMusicChanged(Music music){
    int position = musicList.indexOf(music);
    if (position!=-1){
        if (playingMusicPos != position){
            notifyItemChanged(playingMusicPos);
            playingMusicPos = position;
            notifyItemChanged(playingMusicPos);
        }
    }
    }

    public interface OnItemClick{
    void OnNewMuClick(Music music,int po);
    }

}
