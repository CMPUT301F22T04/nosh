package com.example.nosh.fragments.plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.activities.NewMealPlanActivity;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.MealPlanComponent;
import com.example.nosh.entity.Recipe;
import com.example.nosh.fragments.plan.RecyclerViews.MealPlanRecyclerViewInterface;
import com.example.nosh.fragments.plan.RecyclerViews.MealPlanRecyclerViewAdapter;
import com.example.nosh.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment implements
        MealPlanRecyclerViewInterface, Observer {

    ArrayList<MealPlan> mealPlans = new ArrayList<>();

    @Inject
    MealPlanController controller;

    public PlanFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance(String param1, String param2) {
        return new PlanFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((Nosh) context.getApplicationContext())
                .getAppComponent()
                .inject(this);

        controller.addObserver(this);

        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        controller.add(generateTestedMealPlan());
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

    @Override
    public void onDestroy() {
        controller.deleteObserver(this);

        super.onDestroy();
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

    private static MealPlan generateTestedMealPlan() {
        MealPlan mealPlan = new MealPlan(
                "Meal Plan 1",
                DateUtil.getCalendar("2022-11-17").getTime(),
                DateUtil.getCalendar("2022-11-27").getTime()
        );

        mealPlan.setHashcode("1");

        Meal mealA = new Meal(5, "breakfast");
        mealA.setHashcode("2");

        Meal mealB = new Meal(10, "lunch");
        mealB.setHashcode("3");

        MealComponent ingredientA = new Ingredient();
        ingredientA.setHashcode("4");
        MealComponent recipeA = new Recipe();
        recipeA.setHashcode("6");

        MealComponent ingredientB = new Ingredient();
        ingredientB.setHashcode("5");
        MealComponent recipeB = new Recipe();
        recipeB.setHashcode("7");

        mealA.addMealComponent(ingredientA);
        mealA.addMealComponent(recipeA);

        mealB.addMealComponent(ingredientB);
        mealB.addMealComponent(recipeB);

        mealPlan.addMealToDay("2022-11-20", mealA);
        mealPlan.addMealToDay("2022-11-25", mealB);

        return mealPlan;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}