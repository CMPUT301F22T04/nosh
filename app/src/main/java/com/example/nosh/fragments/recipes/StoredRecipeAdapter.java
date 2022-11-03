package com.example.nosh.fragments.recipes;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.fragments.ingredients.StoredIngredientAdapter;

import java.util.ArrayList;


public class StoredRecipeAdapter extends RecyclerView.Adapter<StoredRecipeViewHolder>{

    private RecyclerViewListener listener; //recycleview listener

    private final Context context;

    private ArrayList<Recipe> recipes; //List of recipes



    interface RecyclerViewListener {
        void onEditClick(int pos);
    }
    //recipe adapter constructor
    public StoredRecipeAdapter(StoredRecipeAdapter.RecyclerViewListener listener, Context context,
                                   ArrayList<Recipe> recipes) {
        this.listener = listener;
        this.context = context;
        this.recipes = recipes;
    }
    void update(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
    @NonNull
    @Override
    public StoredRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stored_recipe_entry, parent, false);

        return new StoredRecipeViewHolder(listener, view);
    }
    @Override
    public void onBindViewHolder(@NonNull StoredRecipeViewHolder holder,
                                 int position) {
        holder.getNameTxtView().setText(recipes.get(position).getTitle());
        /**
         * THIS HOLDER NEED TO BE EDITED FOR IMAGES
         */
        holder.getImageView().setImageURI(Uri.parse(recipes.get(position).getPhotograph())
                );

    }
    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
