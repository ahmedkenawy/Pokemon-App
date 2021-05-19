package com.ahmedkenawy.pokemon.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ahmedkenawy.pokemon.model.Pokemon;

import java.util.List;

import javax.inject.Inject;

@Dao
public interface PokemonDao {

    @Insert
    public void inertPokemon(Pokemon pokemon);

    @Query("delete from fav_table where name=:pokemonName")
    public void deletePokemon(String pokemonName);

    @Query("select * from fav_table")
    public LiveData<List<Pokemon>> getPokemons();

}
