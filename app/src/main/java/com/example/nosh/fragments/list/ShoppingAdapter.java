package com.example.nosh.fragments.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;


public class ShoppingAdapter extends
        RecyclerView.Adapter<ShoppingViewHolder> {

    private final RecyclerViewListener listener;

    private final Context context;

    private ArrayList<Ingredient> ingredients;

    public ShoppingAdapter(RecyclerViewListener listener, Context context,
                           ArrayList<Ingredient> ingredients) {
        this.listener = listener;
        this.context = context;
        this.ingredients = ingredients;
    }

    public interface RecyclerViewListener {
        void onCheckBoxClick(int pos);
    }

    public void update(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shopping_entry, parent, false);

        return new ShoppingViewHolder(listener, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        holder.getNameTxtView().setText(ingredients.get(position).getName());
        holder.getDescriptionTxtView().setText(String.valueOf(ingredients.get(position).
                getDescription()));
        holder.getAmountTxtView().setText(String.valueOf("Amount: " + ingredients.get(position).
                getAmount()));
        holder.getUnitTxtView().setText(String.valueOf("Unit: " + ingredients.get(position).
                getUnit()));
        holder.getCategoryTxtView().setText(String.valueOf("Category: " + ingredients.get(position).
                getCategory()));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}