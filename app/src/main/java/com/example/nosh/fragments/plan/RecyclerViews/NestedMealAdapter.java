package com.example.nosh.fragments.plan.RecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        private final TextView servingsTextView;
        private final CardView cardView;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.meal_name);
            servingsTextView = itemView.findViewById(R.id.textView11);
            cardView = itemView.findViewById(R.id.meal_cardView);
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getServingsTextView() {
            return servingsTextView;
        }

        public CardView getCardView() { return cardView; }
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
        holder.cardView.setOnClickListener(v -> {
            Integer i = position;
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

}