package com.example.nosh.fragments.plan.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.utils.DateUtil;

import java.util.ArrayList;


public class MealPlanRecyclerViewAdapter
        extends RecyclerView.Adapter<MealPlanRecyclerViewAdapter.MealPlanViewHolder> {

    private final Context context;

    private final MealPlanRecyclerViewListener listener;

    private ArrayList<MealPlan> mealPlans;

    public MealPlanRecyclerViewAdapter(Context context, ArrayList<MealPlan> mealPlans,
                                       MealPlanRecyclerViewListener listener){
        this.context = context;
        this.listener = listener;
        this.mealPlans = mealPlans;

    }

    @NonNull
    @Override
    public MealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.meal_plan_recycler_view_row, parent, false);

        return new MealPlanViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MealPlanViewHolder holder, int position) {

        // assign value to views
        holder.planName.setText(mealPlans.get(position).getName());

        String description = "\uD83D\uDCC5 from "
                + DateUtil.formatDate(mealPlans.get(position).getStartDate())
                + " to "
                + DateUtil.formatDate(mealPlans.get(position).getEndDate());

        holder.planSpan.setText(description);
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public void update(ArrayList<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public static class MealPlanViewHolder extends RecyclerView.ViewHolder{
        // grabbing the views from recycler layout
        TextView planName;
        TextView planSpan;
        ImageButton deleteButton;

        private final class MealPlanViewHolderListener implements View.OnClickListener {

            private final MealPlanRecyclerViewListener listener;

            MealPlanViewHolderListener(MealPlanRecyclerViewListener listener) {
                this.listener = listener;
            }

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (v.getId() == deleteButton.getId()) {
                        listener.onDeleteItemClick(position);
                    } else {
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            }
        }

        public MealPlanViewHolder(@NonNull View itemView,
                                  MealPlanRecyclerViewListener recyclerViewListener) {
            super(itemView);

            planName = itemView.findViewById(R.id.plan_name);
            planSpan = itemView.findViewById(R.id.plan_span);
            deleteButton = itemView.findViewById(R.id.delete_meal_plan_button);

            MealPlanViewHolderListener listener =
                    new MealPlanViewHolderListener(recyclerViewListener);

            deleteButton.setOnClickListener(listener);
            itemView.setOnClickListener(listener);
        }
    }
}
