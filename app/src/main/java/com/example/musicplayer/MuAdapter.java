package com.example.musicplayer;

import android.content.Context;
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
    Context context;
    FaveDao faveDao;

    MuAdapter(List<Music> music, OnItemClick onItemClick, Context context) {
        this.musicList = music;
        this.onItemClick = onItemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public MuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MuViewHolder holder, int position) {
        holder.bind(musicList.get(position));
        final Music music_item = musicList.get(position);

        if (faveDao.isFave(music_item.getId()) == 1) {
            holder.faveBtn.setImageResource(R.drawable.ic_baseline_favorite_24);

        } else {
            holder.faveBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        holder.faveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModelFave dataModelFave = new DataModelFave();
                int id = music_item.getId();
                dataModelFave.setId(id);
                if (faveDao.isFave(music_item.getId()) != 1) {
                    holder.faveBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
                    faveDao.addData(dataModelFave);
                } else {
                    holder.faveBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    faveDao.delete(dataModelFave);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MuViewHolder extends RecyclerView.ViewHolder {


        TextView name, artist;
        ImageView imageView, faveBtn;
        LottieAnimationView lottieAnimationView;

        public MuViewHolder(@NonNull View itemView) {
            super(itemView);
            artist = itemView.findViewById(R.id.tv_artist_name);
            name = itemView.findViewById(R.id.tv_mu);
            imageView = itemView.findViewById(R.id.iv_art);
            lottieAnimationView = itemView.findViewById(R.id.animateview);
            faveBtn = itemView.findViewById(R.id.iv_fave);
        }

        public void bind(Music music) {
            faveDao = DataBaseFave.dataBaseFave(context).getFaveDao();
            name.setText(music.getArtist());
            artist.setText(music.getName());

            imageView.setImageResource(music.getCoverResId());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.OnNewMuClick(music, getAdapterPosition());
                }
            });
            if (getAdapterPosition() == playingMusicPos) {
                lottieAnimationView.setVisibility(View.VISIBLE);

            } else {
                lottieAnimationView.setVisibility(View.GONE);
            }


        }


    }

    public void notifyMusicChanged(Music music) {
        int position = musicList.indexOf(music);
        if (position != -1) {
            if (playingMusicPos != position) {
                notifyItemChanged(playingMusicPos);
                playingMusicPos = position;
                notifyItemChanged(playingMusicPos);
            }
        }
    }

    public interface OnItemClick {
        void OnNewMuClick(Music music, int po);

        void OnFaveBtnClicked(Music music);

    }

}
