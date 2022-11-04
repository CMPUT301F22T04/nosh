package com.example.nosh.fragments.recipes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;

public class RecipeIngredientAdapter extends
        RecyclerView.Adapter<RecipeIngredientViewHolder> {

    private final com.example.nosh.fragments.recipes.RecipeIngredientAdapter.RecyclerViewListener listener;

    private final Context context;

    private ArrayList<Ingredient> ingredients;

    public RecipeIngredientAdapter(com.example.nosh.fragments.recipes.RecipeIngredientAdapter.RecyclerViewListener listener, Context context,
                             ArrayList<Ingredient> ingredients) {
        this.listener = listener;
        this.context = context;
        this.ingredients = ingredients;
    }

    interface RecyclerViewListener {
        void onDeleteButtonClick(int pos);
        void onEditClick(int pos);
    }

    void update(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stored_ingredient_in_recipe, parent, false);

        return new RecipeIngredientViewHolder(listener, view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder holder,
                                 int position) {
        holder.getNameTxtView().setText(ingredients.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}

