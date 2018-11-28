package com.example.indus.businesscard.database;

import android.content.Context;

import com.example.indus.businesscard.modeldto.NewsEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "NewsRoomDB.db";
    private static NewsDatabase sInstance;

    public abstract NewsDAO getNewsDao();

    public static NewsDatabase getAppDatabase(Context context) {
        if (sInstance == null) {
            synchronized (NewsDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NewsDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }
}
