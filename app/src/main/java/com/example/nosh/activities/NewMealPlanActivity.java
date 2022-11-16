package com.example.nosh.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.nosh.MainActivity;
import com.example.nosh.R;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.utils.DateUtil;


/**
 * This activity handles the creation of a new meal plan, it will register the new meal plan in
 * firebase and pass it to the next activity where meals will be defined
 */
public class NewMealPlanActivity extends AppCompatActivity {
    private EditText planName;
    private EditText planStart;
    private EditText planEnd;

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
             launchAddMealsToDaysActivity(createNewMealPlan());
         });

         cancelButton.setOnClickListener(view -> {
             Intent intent = new Intent(this, MainActivity.class);
             startActivity(intent);
         });
    }

    MealPlan createNewMealPlan(){
        return new MealPlan(planName.getText().toString(),
                DateUtil.getCalendar(planStart.getText().toString()).getTime(),
                DateUtil.getCalendar(planEnd.getText().toString()).getTime());
    }

    private void launchAddMealsToDaysActivity(MealPlan mealPlan){
        Intent intent = new Intent(this, AddMealsToDaysActivity.class);
        intent.putExtra("meal_plan", mealPlan);
        startActivity(intent);
    }
}