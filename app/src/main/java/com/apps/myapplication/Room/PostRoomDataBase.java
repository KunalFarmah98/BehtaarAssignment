package com.apps.myapplication.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Post.class}, version = 1)
public abstract class PostRoomDataBase extends RoomDatabase {

    private static PostRoomDataBase INSTANCE;

    public abstract PostDao PostDao();

    public static synchronized PostRoomDataBase getInstance(Context mContext) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(mContext.getApplicationContext(),
                    PostRoomDataBase.class, "PostRoomDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
