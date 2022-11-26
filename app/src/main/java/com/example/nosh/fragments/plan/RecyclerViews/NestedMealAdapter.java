package com.example.nosh.fragments.plan.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Meal;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class NestedMealAdapter extends
        RecyclerView.Adapter<NestedMealAdapter.NestedViewHolder> {

    private List<Meal> meals;

    public NestedMealAdapter(List<Meal> meals){
        this.meals = meals;
    }

    protected static class NestedViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private final TextView servingsTextView;
        private final TextView components;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.meal_name);
            servingsTextView = itemView.findViewById(R.id.textView11);
            components = itemView.findViewById(R.id.components);
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getServingsTextView() {
            return servingsTextView;
        }

        public TextView getComponents() { return components; }

    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.meal_item , parent , false);

        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.getTextView().setText(meals.get(position).getName());
        holder.getServingsTextView().setText(Integer.toString((int) meals.get(position).getServings()));

        Meal meal = meals.get(position);
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i = 0; i < meal.getMealComponents().size(); i++){
            String name = meal.getMealComponents().get(i).getName();
            stringBuilder.append("\uD83C\uDF5C ").append(name).append("\n");
        }
        holder.getComponents().setText(stringBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

}