package com.example.nosh.fragments.plan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;

import java.util.ArrayList;

public class MealPlan_RecyclerViewAdapter extends RecyclerView.Adapter<MealPlan_RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MockMealPlan> mealPlans;

    public MealPlan_RecyclerViewAdapter(Context context, ArrayList<MockMealPlan> mealPlans){
        this.context = context;
        this.mealPlans = mealPlans;
    }

    @NonNull
    @Override
    public MealPlan_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.meal_plan_recycler_view_row, parent, false);

        return new MealPlan_RecyclerViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlan_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assign value to views
        holder.planName.setText(mealPlans.get(position).getName());
        holder.planSpan.setText(mealPlans.get(position).getSpan());
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing the views from recycler layout
        TextView planName;
        TextView planSpan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            planName = itemView.findViewById(R.id.plan_name);
            planSpan = itemView.findViewById(R.id.plan_span);
        }
    }
}
