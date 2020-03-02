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
   private Integer id_frete;

   @ColumnInfo(name = "latitude")
   private String latitude;

   @ColumnInfo(name = "longitude")
   private String longitude;

   @ColumnInfo(name = "data_transacao")
   private String data_transacao;

   @ColumnInfo(name = "sincronizado")
   private String sincronizado; // Sim/Não

   public Location(Integer id_frete, String latitude, String longitude, String data_transacao) {
      this.id_frete = id_frete;
      this.latitude = latitude;
      this.longitude = longitude;
      this.data_transacao = data_transacao;
      this.sincronizado = "Não";
   }
   
   public Integer id(){return this.id;}
   public Integer id_frete(){return this.id_frete;}
   public String latitude(){return this.latitude;}
   public String longitude(){return this.longitude;}
   public String data_transacao(){return this.data_transacao;}
   public String sincronizado(){return this.sincronizado;}
   public void setId(Integer id){
      this.id = id;
   }
   public void setSincronizado(String sincronizado){
      this.sincronizado = sincronizado;
   }
}