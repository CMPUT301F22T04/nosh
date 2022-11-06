package com.example.nosh.fragments.plan.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Foodstuff;
import com.example.nosh.entity.Meal;
import com.example.nosh.fragments.plan.MealDay;

import java.util.ArrayList;

public class MealSpan_RecyclerViewAdapter extends RecyclerView.Adapter<MealSpan_RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MealDay> mealDays;

    public MealSpan_RecyclerViewAdapter(Context context, ArrayList<MealDay> mealDays){
        this.context = context;
        this.mealDays = mealDays;
    }

    @NonNull
    @Override
    public MealSpan_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.meal_plan_span_recycler_view, parent, false);

        return new MealSpan_RecyclerViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealSpan_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.dayOfPlan.setText(mealDays.get(position).getDay());

        ArrayList<Meal> meals = new ArrayList<>();

        String[] names = {"Meal 1", "Meal 2", "Meal 3"};

        for (int i = 0; i < names.length; i++){
            meals.add(new Meal(names[i]));
        }

        Meal_RecyclerViewAdapter adapter = new Meal_RecyclerViewAdapter(context, meals);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return mealDays.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing the views from recycler layout
        TextView dayOfPlan;
        TextView planSpan;
        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dayOfPlan = itemView.findViewById(R.id.day_of_plan);
            planSpan = itemView.findViewById(R.id.plan_span);

            recyclerView = itemView.findViewById(R.id.meal_recycler_view);
        }
    }
}
