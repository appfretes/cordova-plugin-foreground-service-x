package com.softniels.foregroundservicex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

   //@PrimaryKey
   //@NonNull
   //@PrimaryKey(autoGenerate = true)
   @NonNull
   @PrimaryKey
   @ColumnInfo(name = "word")
   private String mWord;

   public Word(String word) {this.mWord = word;}

   public String getWord(){return this.mWord;}
}