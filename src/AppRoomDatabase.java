package com.softniels.foregroundservicex;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Location.class, Event.class}, version = 3, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

   public abstract LocationDao locationDao();
   public abstract EventDao eventDao();

   private static volatile AppRoomDatabase INSTANCE;
   private static final int NUMBER_OF_THREADS = 4;
   static final ExecutorService databaseWriteExecutor =
        Executors.newFixedThreadPool(NUMBER_OF_THREADS);

   static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "ojo")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration() // Remover em produção e adicionar migração corretamente //
                                // Ler mais em https://developer.android.com/training/data-storage/room/migrating-db-versions
                                // Ler mais em https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}