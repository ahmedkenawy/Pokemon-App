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
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private PokemonViewModel viewModel;
    Button goToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        goToHome = findViewById(R.id.gotohome);

        recyclerView = findViewById(R.id.favRecyclerView);
        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getAllPokemons();
        viewModel.getFavList().observe(this, pokemons -> {
            ArrayList<Pokemon>  list=new ArrayList<>();
            list.addAll(pokemons);
            adapter.setList(list);
        });
        setupSwipe();
        goToHome.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
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
                viewModel.deletePokemon(pokemon.getName());
                adapter.notifyDataSetChanged();
                Toast.makeText(FavoriteActivity.this, pokemon.getName() + "Deleted From Favorites", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}