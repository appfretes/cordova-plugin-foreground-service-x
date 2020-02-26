package com.softniels.foregroundservicex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

@Entity(tableName = "event")
public class Event {

   //@PrimaryKey
   //@NonNull
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
   private Integer id;

   @ColumnInfo(name = "event")
   private String event;

   @ColumnInfo(name = "value")
   private String value;

   public Event(Integer id, String event, String value) {
    this.id = 0;
    this.event = event;
    this.value = value;
   }
   
   public Integer getId(){return this.id;}
   public String getEvent(){return this.event;}
   public String getValue(){return this.value;}
}