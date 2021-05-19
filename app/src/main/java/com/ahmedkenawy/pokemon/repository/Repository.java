package com.ahmedkenawy.pokemon.repository;

import androidx.lifecycle.LiveData;

import com.ahmedkenawy.pokemon.Network.PokemonApiService;
import com.ahmedkenawy.pokemon.db.PokemonDao;
import com.ahmedkenawy.pokemon.model.Pokemon;
import com.ahmedkenawy.pokemon.model.PokemonResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private PokemonApiService pokemonApiService;
    private PokemonDao pokemonDao;


    @Inject
    public Repository(PokemonApiService pokemonApiService,PokemonDao pokemonDao) {
        this.pokemonApiService = pokemonApiService;
        this.pokemonDao=pokemonDao;
    }

    public Observable<PokemonResponse> getPokemons(){
       return pokemonApiService.getPokemon();
    }

    public void inertPokemon(Pokemon pokemon){
        pokemonDao.inertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName){
        pokemonDao.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getFavPokemons(){
       return pokemonDao.getPokemons();
    }
}
