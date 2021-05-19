package com.ahmedkenawy.pokemon.di;

import android.app.Application;

import androidx.room.Room;

import com.ahmedkenawy.pokemon.db.PokemonDB;
import com.ahmedkenawy.pokemon.db.PokemonDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    public static PokemonDB provideDB(Application application) {

        return Room.databaseBuilder(application, PokemonDB.class, "fav_Db").fallbackToDestructiveMigration()

                .allowMainThreadQueries().build();
    }
    @Provides
    @Singleton
    public static PokemonDao provideDao(PokemonDB db){
        return db.pokemonDao();
    }
}
