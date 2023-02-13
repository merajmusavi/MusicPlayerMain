package com.example.musicplayer;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface DaoData {


    @Insert
    long addData(DataModel dataModel);

}
