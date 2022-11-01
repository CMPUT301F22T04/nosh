package com.example.nosh.fragments.plan.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.fragments.plan.Meal;

import java.util.ArrayList;

public class Meal_RecyclerViewAdapter extends RecyclerView.Adapter<Meal_RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Meal> meals;

    public Meal_RecyclerViewAdapter(Context context, ArrayList<Meal> meals){
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public Meal_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.meal_recycler_row, parent, false);

        return new Meal_RecyclerViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Meal_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.mealName.setText(meals.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing the views from recycler layout
        TextView mealName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.meal_name);
        }
    }
}
