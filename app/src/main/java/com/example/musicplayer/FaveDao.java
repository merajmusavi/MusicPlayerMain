package com.example.musicplayer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FaveDao {

    @Insert
    void addData(DataModelFave dataModelFave);


    @Query("SELECT EXISTS (SELECT 1 FROM fave_db WHERE id=:id)")
    int isFave(int id);

    @Delete
    void delete(DataModelFave dataModelFave);
}
