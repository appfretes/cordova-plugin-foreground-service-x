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

   @ColumnInfo(name = "id_frete")
   private Integer id_frete;

   @ColumnInfo(name = "event")
   private String event;

   @ColumnInfo(name = "value")
   private String value;

   public Event(Integer id, Integer id_frete, String event, String value) {
    this.id = id;
    this.id_frete = id_frete;
    this.event = event;
    this.value = value;
   }
   
   public Integer id(){return this.id;}
   public Integer id_frete(){return this.id_frete;}
   public String event(){return this.event;}
   public String value(){return this.value;}
   public void setId(Integer id){
      this.id = id;
   }
}