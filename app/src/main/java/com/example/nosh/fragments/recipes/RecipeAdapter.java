package com.example.nosh.fragments.recipes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Recipe;

import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final Context context;

    private final RecyclerViewListener listener;

    private ArrayList<Recipe> recipes;

    RecipeAdapter(ArrayList<Recipe> recipes, Context context, RecyclerViewListener listener) {
        this.context = context;
        this.listener = listener;
        this.recipes = recipes;
    }

    interface RecyclerViewListener {
        void onEditClick(int pos);
    }

    void update(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recipe_entry, parent, false);

        return new RecipeViewHolder(listener, view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.getNameTextView().setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
