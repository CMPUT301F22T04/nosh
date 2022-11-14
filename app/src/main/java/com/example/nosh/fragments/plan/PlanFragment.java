package com.example.nosh.fragments.plan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nosh.R;
import com.example.nosh.activities.NewMealPlanActivity;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.fragments.plan.RecyclerViews.MealPlanRecyclerViewInterface;
import com.example.nosh.fragments.plan.RecyclerViews.MealPlanRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment implements MealPlanRecyclerViewInterface {
    ArrayList<MealPlan> mealPlans = new ArrayList<>();

    public PlanFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.plan_recycler_view);

        setUpTestData();

        MealPlanRecyclerViewAdapter adapter = new MealPlanRecyclerViewAdapter(getContext(), mealPlans, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button newMealPlanButton = v.findViewById(R.id.new_meal_plan_button);

        newMealPlanButton.setOnClickListener(view -> {
            launchNewMealPlanActivity();
        });

        return v;
    }

    private void setUpTestData(){
        String[] planNames = {"Vegan Diet", "Uni Meal Prep", "Mom's Recipes"};
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 4);
        Date tomorrow = calendar.getTime();

        for (int i = 0; i < planNames.length; i++){
            mealPlans.add(new MealPlan(planNames[i], today, tomorrow));
        }
    }

    @Override
    public void onItemClick(int position) {
        // TODO: put a fragment container in this xml file instead of replacing the main one???
        openMealsOfDayDialog();
    }

    private void openMealsOfDayDialog() {
        MealsOfDayDialog mealsOfDayDialog = MealsOfDayDialog.newInstance();
        mealsOfDayDialog.show(getParentFragmentManager(), "MEALS_OF_DAY");
    }

    private void launchNewMealPlanActivity() {
        Intent intent = new Intent(getContext(), NewMealPlanActivity.class);
        startActivity(intent);
    }
}