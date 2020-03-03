package com.softniels.foregroundservicex;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   void insert(Event event);

   @Query("DELETE FROM event")
   void deleteAll();

   @Query("SELECT * FROM event")
   List<Event> getAll();

   @Query("SELECT * FROM event WHERE id_frete = :idFrete")
   List<Event> getAll(Integer idFrete);

   @Query("SELECT * FROM event LIMIT 1")
   Event findUnique();

   @Query("SELECT * FROM event ORDER BY id DESC LIMIT 1")
   Event findLast();
}