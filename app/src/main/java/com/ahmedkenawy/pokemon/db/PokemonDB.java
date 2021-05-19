package com.ahmedkenawy.pokemon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ahmedkenawy.pokemon.model.Pokemon;

import javax.inject.Inject;

import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Database(entities = Pokemon.class,version = 1,exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {

    public abstract PokemonDao pokemonDao();
}
