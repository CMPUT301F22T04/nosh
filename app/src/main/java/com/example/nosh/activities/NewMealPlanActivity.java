package com.example.nosh.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nosh.MainActivity;
import com.example.nosh.R;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.fragments.plan.AddMealsToDaysDialog;
import com.example.nosh.fragments.plan.MealsOfDayDialog;

import java.util.Date;

public class NewMealPlanActivity extends AppCompatActivity {
    private EditText planName;
    private EditText planStart;
    private EditText planEnd;
    private MealPlan newMealPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal_plan);

         planName = findViewById(R.id.new_meal_plan_name);
         planStart = findViewById(R.id.new_meal_plan_start);
         planEnd = findViewById(R.id.new_meal_plan_end);

         Button nextStepButton = findViewById(R.id.finish_new_meal_step1_button);
         Button cancelButton = findViewById(R.id.cancel_new_meal_plan_button);

         nextStepButton.setOnClickListener(view -> {
             // TODO: Fix the dates to actually be dates
             newMealPlan = new MealPlan(planName.getText().toString(), new Date(), new Date());
             launchAddMealsToDaysActivity(newMealPlan);
         });

         cancelButton.setOnClickListener(view -> {
             Intent intent = new Intent(this, MainActivity.class);
             startActivity(intent);
         });
    }

    private void launchAddMealsToDaysActivity(MealPlan mealPlan){
        Intent intent = new Intent(this, AddMealsToDaysActivity.class);
        intent.putExtra("meal_plan", mealPlan);
        startActivity(intent);
    }
}