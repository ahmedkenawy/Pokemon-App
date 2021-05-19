package com.ahmedkenawy.pokemon.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.inject.Inject;

@Entity(tableName = "fav_table")
public class Pokemon {
   @PrimaryKey(autoGenerate = true)
   private int id;
   private String name;
   private String url;
   @Inject
    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
