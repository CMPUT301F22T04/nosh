package com.example.nosh.fragments.recipes;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nosh.R;
import com.example.nosh.entity.Recipe;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final Context context;

    private final RecyclerViewListener listener;

    private ArrayList<Recipe> recipes;
    private HashMap<String, StorageReference> recipeImagesRemote;

    RecipeAdapter(ArrayList<Recipe> recipes, Context context,
                  HashMap<String, StorageReference> recipeImagesRemote,
                  RecyclerViewListener listener) {
        this.context = context;
        this.listener = listener;
        this.recipes = recipes;
        this.recipeImagesRemote = recipeImagesRemote;
    }



    interface RecyclerViewListener {
        void onEditClick(int pos);
    }

    void update(ArrayList<Recipe> recipes, HashMap<String, StorageReference> recipeImagesRemote) {
        this.recipes = recipes;
        this.recipeImagesRemote = recipeImagesRemote;
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
        Glide.with(context)
                .load(recipeImagesRemote.get(recipes.get(position).getPhotographRemote()))
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
