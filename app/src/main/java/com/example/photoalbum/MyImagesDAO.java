package com.example.photoalbum;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyImagesDAO {
    @Insert
    void insert(MyImages myImages);

    @Update
    void update(MyImages myImages);

    @Delete
    void delete(MyImages myImages);

    @Query("SELECT * FROM my_images ORDER BY imageId ASC")
    LiveData<List<MyImages>> getMyImages();
}
