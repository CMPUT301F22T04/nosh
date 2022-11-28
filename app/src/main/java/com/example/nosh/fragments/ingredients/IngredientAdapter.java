package com.example.nosh.fragments.ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;

/**
 * This class is responsible for the Ingredient class RecyclerView adapter.
 */

public class IngredientAdapter extends
        RecyclerView.Adapter<IngredientViewHolder> {

    private final RecyclerViewListener listener;

    private final Context context;

    private ArrayList<Ingredient> ingredients;

    public IngredientAdapter(RecyclerViewListener listener, Context context,
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
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_entry, parent, false);

        return new IngredientViewHolder(listener, view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder,
                                 int position) {
        holder.getNameTxtView().setText(ingredients.get(position).getName());

        String description = "\uD83D\uDCDD " + ingredients.get(position).getDescription();

        holder.getDescriptionTxtView().setText(description);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
