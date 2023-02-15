package com.example.musicplayer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(version = 2,exportSchema = false,entities = {DataModelFave.class})
public abstract class DataBaseFave extends RoomDatabase {
    private static DataBaseFave dataBaseFave;

    public static DataBaseFave dataBaseFave(Context context){
        if (dataBaseFave==null){
            dataBaseFave = Room.databaseBuilder(context.getApplicationContext(),DataBaseFave.class,"fave_db").
                    allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dataBaseFave;

    }

    public abstract FaveDao getFaveDao();


}
