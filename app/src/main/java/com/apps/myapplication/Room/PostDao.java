package com.apps.myapplication.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post repo);

    @Query("SELECT * from POST ORDER BY ID ASC")
    LiveData<List<Post>> getAllPosts();

    @Query("DELETE FROM POST")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<Post> Posts);

}
