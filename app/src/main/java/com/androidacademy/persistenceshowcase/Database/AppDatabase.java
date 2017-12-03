package com.androidacademy.persistenceshowcase.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.androidacademy.persistenceshowcase.Models.Film;
import com.androidacademy.persistenceshowcase.Network.NetworkManager;

@Database(entities = {Film.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singleton;
    private static final String DATABASE_NAME = "FilmDb.db";

    public abstract FilmDao filmDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (singleton == null) {
            synchronized (AppDatabase.class) {
                if (singleton == null) {
                    singleton = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return singleton;
    }

}
