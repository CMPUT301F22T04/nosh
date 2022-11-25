package com.example.nosh.fragments.plan.RecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Meal;

import java.util.List;

public class NestedMealAdapter extends
        RecyclerView.Adapter<NestedMealAdapter.NestedViewHolder> {

    private List<Meal> meals;

    public NestedMealAdapter(List<Meal> meals){
        this.meals = meals;
    }

    protected static class NestedViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.meal_name);
        }

        public TextView getTextView() {
            return textView;
        }
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
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

}