package com.ahmedkenawy.pokemon.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedkenawy.pokemon.R;
import com.ahmedkenawy.pokemon.model.Pokemon;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    ArrayList<Pokemon> pokemonArrayList = new ArrayList<>();
    private Context mContext;

    public void setList(ArrayList<Pokemon> pokemonArrayList) {
        this.pokemonArrayList = pokemonArrayList;
        notifyDataSetChanged();
    }

    public PokemonAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public Pokemon getPokemon(int position){
        return pokemonArrayList.get(position);
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemoncustomview, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.textView.setText(pokemonArrayList.get(position).getName());
        Glide.with(mContext)
                .load(pokemonArrayList.get(position).getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return pokemonArrayList.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pokemonImage);
            textView = itemView.findViewById(R.id.pokemonTV);
        }
    }

}
