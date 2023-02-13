package com.example.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_data")
public class DataModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Lyrics = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLyrics() {
        return Lyrics;
    }

    public void setLyrics(String lyrics) {
        Lyrics = lyrics;
    }

    public Boolean getInterest() {
        return isInterest;
    }

    public void setInterest(Boolean interest) {
        isInterest = interest;
    }

    private Boolean isInterest;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.Lyrics);
        dest.writeValue(this.isInterest);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.Lyrics = source.readString();
        this.isInterest = (Boolean) source.readValue(Boolean.class.getClassLoader());
    }

    public DataModel() {
    }

    protected DataModel(Parcel in) {
        this.id = in.readInt();
        this.Lyrics = in.readString();
        this.isInterest = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DataModel> CREATOR = new Parcelable.Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel source) {
            return new DataModel(source);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
}
