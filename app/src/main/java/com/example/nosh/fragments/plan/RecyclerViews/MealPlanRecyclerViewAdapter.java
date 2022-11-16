package com.example.nosh.fragments.plan.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.MealPlan;

import java.util.ArrayList;

public class MealPlanRecyclerViewAdapter extends RecyclerView.Adapter<MealPlanRecyclerViewAdapter.MyViewHolder> {
    private MealPlanRecyclerViewInterface mealPlanRecyclerViewInterface;

    private Context context;
    private ArrayList<MealPlan> mealPlans;

    public MealPlanRecyclerViewAdapter(Context context, ArrayList<MealPlan> mealPlans, MealPlanRecyclerViewInterface mealPlanRecyclerViewInterface){
        this.context = context;
        this.mealPlans = mealPlans;
        this.mealPlanRecyclerViewInterface = mealPlanRecyclerViewInterface;
    }

    @NonNull
    @Override
    public MealPlanRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.meal_plan_recycler_view_row, parent, false);

        return new MealPlanRecyclerViewAdapter.MyViewHolder(v, mealPlanRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanRecyclerViewAdapter.MyViewHolder holder, int position) {
        // assign value to views
        holder.planName.setText(mealPlans.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing the views from recycler layout
        TextView planName;

        public MyViewHolder(@NonNull View itemView, MealPlanRecyclerViewInterface mealPlanRecyclerViewInterface) {
            super(itemView);

            planName = itemView.findViewById(R.id.plan_name);

            itemView.setOnClickListener(view -> {
                if(mealPlanRecyclerViewInterface != null){
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        mealPlanRecyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
