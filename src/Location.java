package com.softniels.foregroundservicex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "location")
public class Location {

   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
   private Integer id;

   @ColumnInfo(name = "latitude")
   private String latitude;

   @ColumnInfo(name = "longitude")
   private String longitude;

   @ColumnInfo(name = "data_transacao")
   private String dataTransacao;

   public Location(String latitude, String longitude, String dataTransacao) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.dataTransacao = dataTransacao;
   }
   
   public Integer getId(){return this.id;}
   public String getLatitude(){return this.latitude;}
   public String getLongitude(){return this.longitude;}
   public String getDataTransacao(){return this.dataTransacao;}
   public void setId(Integer id){
      this.id = id;
   }
}