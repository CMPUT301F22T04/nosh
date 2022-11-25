package com.example.nosh.fragments.plan.RecyclerViews;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.MealPlanComponent;

import java.util.List;


public class PlanDayRecyclerViewAdapter extends
        RecyclerView.Adapter<PlanDayRecyclerViewAdapter.ItemViewHolder> {

    List<Pair<String, MealPlanComponent>> mealPlanComponents;

    public PlanDayRecyclerViewAdapter(
            List<Pair<String, MealPlanComponent>> mealPlanComponents) {

        this.mealPlanComponents = mealPlanComponents;
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateTextView;
        private final ImageView arrowImageView;
        private final LinearLayout linearLayout;
        private final RelativeLayout expandableLayout;
        private final RecyclerView mealComponentRecyclerView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.itemTv);
            arrowImageView = itemView.findViewById(R.id.arro_imageview);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            mealComponentRecyclerView = itemView.findViewById(R.id.child_rv);
        }

        public TextView getTextView() {
            return dateTextView;
        }

        public ImageView getArrowImageView() {
            return arrowImageView;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public RelativeLayout getExpandableLayout() {
            return expandableLayout;
        }

        public RecyclerView getMealComponentRecyclerView() {
            return mealComponentRecyclerView;
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.meal_plan_item , parent , false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Pair<String, MealPlanComponent> pair = mealPlanComponents.get(position);

        holder.getTextView().setText(pair.first);
        holder.getArrowImageView().setImageResource(R.drawable.arrow_down);

        NestedMealAdapter adapter = new NestedMealAdapter(pair.second.getMeals());

        holder
                .getMealComponentRecyclerView()
                .setLayoutManager(
                        new LinearLayoutManager(holder.itemView.getContext())
                );
        holder.getMealComponentRecyclerView().setHasFixedSize(true);
        holder.getMealComponentRecyclerView().setAdapter(adapter);
        holder.getLinearLayout().setOnClickListener(v -> {
            if (holder.getExpandableLayout().getVisibility() == View.GONE) {
                holder.getExpandableLayout().setVisibility(View.VISIBLE);
                notifyItemChanged(holder.getAdapterPosition());
            } else {
                holder.getExpandableLayout().setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealPlanComponents.size();
    }
}
