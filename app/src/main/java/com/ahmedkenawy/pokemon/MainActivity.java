package com.ahmedkenawy.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmedkenawy.pokemon.adapter.PokemonAdapter;
import com.ahmedkenawy.pokemon.model.Pokemon;
import com.ahmedkenawy.pokemon.viewmodel.PokemonViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private PokemonViewModel viewModel;
    Button goToFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.mainRecyclerView);
        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {


            viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        setupSwipe();
        viewModel.getPokemons();
        viewModel.getPokemonList().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                adapter.setList(pokemons);
            }
        });

        goToFav = findViewById(R.id.gotofav);
        goToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setupSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pokemonPosition = viewHolder.getAdapterPosition();
                Pokemon pokemon = adapter.getPokemon(pokemonPosition);
                viewModel.insertPokemon(pokemon);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, pokemon.getName() + "Added To Favorites", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}