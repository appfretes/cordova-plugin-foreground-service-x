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

   @ColumnInfo(name = "id_frete")
   private Integer idFrete;

   @ColumnInfo(name = "latitude")
   private String latitude;

   @ColumnInfo(name = "longitude")
   private String longitude;

   @ColumnInfo(name = "data_transacao")
   private String dataTransacao;

   @ColumnInfo(name = "sincronizado")
   private String sincronizado; // Sim/Não

   public Location(Integer idFrete, String latitude, String longitude, String dataTransacao) {
      this.idFrete = idFrete;
      this.latitude = latitude;
      this.longitude = longitude;
      this.dataTransacao = dataTransacao;
      this.sincronizado = "Não";
   }
   
   public Integer getId(){return this.id;}
   public Integer getIdFrete(){return this.idFrete;}
   public String getLatitude(){return this.latitude;}
   public String getLongitude(){return this.longitude;}
   public String getDataTransacao(){return this.dataTransacao;}
   public String getSincronizado(){return this.sincronizado;}
   public void setId(Integer id){
      this.id = id;
   }
}