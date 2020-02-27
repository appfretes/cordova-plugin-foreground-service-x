package com.softniels.foregroundservicex;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   void insert(Location location);

   @Query("DELETE FROM location")
   void deleteAll();

   @Query("SELECT * FROM location")
   List<Location> getAll();

   @Query("SELECT * FROM location ORDER BY id DESC LIMIT 1")
   Event findLast();
}