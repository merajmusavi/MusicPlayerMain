package com.example.musicplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Music {
    private int id;
    private String name;
    private String artist;
    private int coverResId;
    private int artistResId;
    private Boolean isInterest;
    public Boolean getIsInterest(){
        return isInterest;
    }
    public void setIsInterest(Boolean isInterest){
        this.isInterest = isInterest;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getCoverResId() {
        return coverResId;
    }

    public void setCoverResId(int coverResId) {
        this.coverResId = coverResId;
    }

    public int getArtistResId() {
        return artistResId;
    }

    public void setArtistResId(int artistResId) {
        this.artistResId = artistResId;
    }

    public static List<Music> getList(){
        List<Music> musicList=new ArrayList<>();
        Music music1 = new Music();
        music1.setId(0);
        music1.setArtist("mohsenchavoshi");
        music1.setName("yad cheshm to mioftam");
        music1.setCoverResId(R.drawable.cover);
        music1.setArtistResId(R.drawable.mohsenchavoshi);
        music1.setIsInterest(false);

        Music music2 = new Music();
        music2.setId(1);
        music2.setArtist("mohsenchavoshi");
        music2.setName("postchi");
        music2.setCoverResId(R.drawable.cover2);
        music2.setArtistResId(R.drawable.mohsenchavoshi);
        music2.setIsInterest(false);

        Music music3 = new Music();
        music3.setId(2);
        music3.setArtist("mohsenchavoshi");
        music3.setName("ghashang man");
        music3.setCoverResId(R.drawable.cover3);
        music3.setArtistResId(R.drawable.mohsenchavoshi);
        music3.setIsInterest(false);

        musicList.add(music2);
        musicList.add(music3);
        musicList.add(music1);
        return musicList;
    }


    public static String convertMillisToString(long durationInMillis) {
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;

        return String.format(Locale.US, "%02d:%02d", minute, second);
    }
}