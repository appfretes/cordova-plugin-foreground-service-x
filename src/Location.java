package com.softniels.foregroundservicex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

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
   private Date data_transacao;

   public Location(String latitude, String longitude, Date data_transacao) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.data_transacao = data_transacao;
   }
   
   public Integer getId(){return this.id;}
   public String getLatitude(){return this.latitude;}
   public String getLongitude(){return this.longitude;}
   public Date getDataTransacao(){return this.data_transacao;}
   public void setId(Integer id){
      this.id = id;
   }
}