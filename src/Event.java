package com.softniels.foregroundservicex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

@Entity(tableName = "event")
public class Event {

   @NonNull
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id_event")
   private String id_event;

   @ColumnInfo(name = "event")
   private String event;

   @ColumnInfo(name = "value")
   private String value;

   public Event(String event, String value) {
    this.event = event;
    this.value = value;
   }
   
   public String getId(){return this.id_event;}
   public String getEvent(){return this.event;}
   public String getValue(){return this.value;}
}