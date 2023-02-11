package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.musicplayer.databinding.ActivityMainBinding;
import com.google.android.material.slider.Slider;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MuAdapter.OnItemClick {
    ActivityMainBinding activityMainBinding;
    List<Music> music = Music.getList();
    private MediaPlayer mediaPlayer;
    private MusicState musicState = MusicState.STOPPED;
    private int cursor = 0;
    MuAdapter muAdapter;

    Timer timer;
    private Boolean isDragging = false;

    @Override
    public void OnNewMuClick(Music music1, int po) {
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
        cursor = po;
        onMusicChange(music.get(cursor));

    }

    enum MusicState {
        PLAYING, PAUSED, STOPPED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        RecyclerView recyclerView = findViewById(R.id.rec_mu);
         muAdapter = new MuAdapter(music,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(muAdapter);

        onMusicChange(music.get(cursor));
        activityMainBinding.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (musicState) {
                    case PLAYING:
                        mediaPlayer.pause();
                        musicState = MusicState.PAUSED;
                        activityMainBinding.ivPlay.setImageResource(R.drawable.ic_play_32dp);
                        break;
                    case PAUSED:
                    case STOPPED:

                        mediaPlayer.start();
                        musicState = MusicState.PLAYING;
                        activityMainBinding.ivPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                        break;
                }
            }
        });
        activityMainBinding.ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMusicToNext();
            }
        });
        activityMainBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        activityMainBinding.slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                activityMainBinding.nowDurationTv.setText(Music.convertMillisToString((long) value));
            }
        });
        activityMainBinding.slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
isDragging = true;
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
isDragging = false;
mediaPlayer.seekTo((int) slider.getValue());
            }
        });
    }

    public void onMusicChange(Music music) {
        muAdapter.notifyMusicChanged(music);
        activityMainBinding.slider.setValue(0);
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
                                if (!isDragging){
                                    activityMainBinding.nowDurationTv.setText(Music.convertMillisToString(mediaPlayer.getCurrentPosition()));
                                activityMainBinding.slider.setValue(mediaPlayer.getCurrentPosition());
                            }}
                        });
                    }
                }, 1000, 1000);
                activityMainBinding.durationTv.setText(Music.convertMillisToString(mediaPlayer.getDuration()));
                musicState = MusicState.PLAYING;
                activityMainBinding.slider.setValueTo(mediaPlayer.getDuration());
                activityMainBinding.ivPlay.setImageResource(R.drawable.ic_baseline_pause_24);
mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    @Override
    public void onCompletion(MediaPlayer mp) {
changeMusicToNext();
    }
});
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
    public void changeMusicToNext(){
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
        if (cursor<music.size()-1){
            cursor+=1;

        }else {
            cursor = 0;

        }
        onMusicChange(music.get(cursor));
    }
    public void ChangeMuToPrev(){
        timer.cancel();
        timer.purge();
        mediaPlayer.release();

        if (cursor==0){
            cursor = music.size()-1;

        }else {
            cursor--;
        }
        onMusicChange(music.get(cursor));
    }
}