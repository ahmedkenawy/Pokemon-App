package com.ahmedkenawy.pokemon.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmedkenawy.pokemon.model.Pokemon;
import com.ahmedkenawy.pokemon.model.PokemonResponse;
import com.ahmedkenawy.pokemon.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Pokemon>> pokemonList = new MutableLiveData<ArrayList<Pokemon>>();
    private Repository repository;
    private LiveData<List<Pokemon>> favList=null;

    public LiveData<List<Pokemon>> getFavList() {
        return favList;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }
    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }


    public void getPokemons() {
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();
                        for (Pokemon pokemon : list) {
                            String url = pokemon.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/" + pokemonIndex[pokemonIndex.length - 1] + ".png");

                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->pokemonList.setValue(result),
                        error-> Log.d("viewModel", error.getMessage()));

    }

    public void  insertPokemon(Pokemon pokemon){
        repository.inertPokemon(pokemon);
    }

    public void  deletePokemon(String pokemonName){
        repository.deletePokemon(pokemonName);
    }

    public void getAllPokemons(){
        favList=repository.getFavPokemons();
    }

}
