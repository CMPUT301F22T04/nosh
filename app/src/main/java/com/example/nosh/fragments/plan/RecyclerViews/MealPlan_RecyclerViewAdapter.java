package com.example.nosh.fragments.plan.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.fragments.plan.MockMealPlan;

import java.util.ArrayList;

public class MealPlan_RecyclerViewAdapter extends RecyclerView.Adapter<MealPlan_RecyclerViewAdapter.MyViewHolder> {
    private MealPlanRecyclerViewInterface mealPlanRecyclerViewInterface;

    private Context context;
    private ArrayList<MockMealPlan> mealPlans;

    public MealPlan_RecyclerViewAdapter(Context context, ArrayList<MockMealPlan> mealPlans, MealPlanRecyclerViewInterface mealPlanRecyclerViewInterface){
        this.context = context;
        this.mealPlans = mealPlans;
        this.mealPlanRecyclerViewInterface = mealPlanRecyclerViewInterface;
    }

    @NonNull
    @Override
    public MealPlan_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.meal_plan_recycler_view_row, parent, false);

        return new MealPlan_RecyclerViewAdapter.MyViewHolder(v, mealPlanRecyclerViewInterface);
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

        public MyViewHolder(@NonNull View itemView, MealPlanRecyclerViewInterface mealPlanRecyclerViewInterface) {
            super(itemView);

            planName = itemView.findViewById(R.id.plan_name);
            planSpan = itemView.findViewById(R.id.plan_span);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if(mealPlanRecyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            mealPlanRecyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
