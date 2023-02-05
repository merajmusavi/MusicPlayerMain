package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.musicplayer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    List<Music> music = Music.getList();
    private MediaPlayer mediaPlayer;
    private MusicState musicState = MusicState.STOPPED;
    Timer timer;

    enum MusicState {
        PLAYING, PAUSED, STOPPED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        RecyclerView recyclerView = findViewById(R.id.rec_mu);
        MuAdapter muAdapter = new MuAdapter(music);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(muAdapter);

        onMusicChange(music.get(1));
    }

    public void onMusicChange(Music music) {
        mediaPlayer = MediaPlayer.create(this, R.raw.music_1);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
        activityMainBinding.nowDurationTv.setText(Music.convertMillisToString(mediaPlayer.getCurrentPosition()));
                           activityMainBinding.slider.setValue(mediaPlayer.getCurrentPosition());
                            }
                        });
                    }
                }, 1000, 1000);
                activityMainBinding.durationTv.setText(Music.convertMillisToString(mediaPlayer.getDuration()));
                musicState = MusicState.PLAYING;
                activityMainBinding.slider.setValueTo(mediaPlayer.getDuration());
                activityMainBinding.ivPlay.setImageResource(R.drawable.ic_baseline_pause_24);

            }
        });
        activityMainBinding.artistIv.setImageResource(music.getArtistResId());
        activityMainBinding.artistTv.setText(music.getArtist());
        activityMainBinding.cover.setImageResource(music.getCoverResId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}