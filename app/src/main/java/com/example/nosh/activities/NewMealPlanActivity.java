package com.example.nosh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nosh.MainActivity;
import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.entity.Transaction;
import com.example.nosh.repository.MealPlanRepository;
import com.example.nosh.utils.DateUtil;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;


/**
 * This activity handles the creation of a new meal plan, it will register the new meal plan in
 * firebase and pass it to the next activity where meals will be defined
 */
public class NewMealPlanActivity extends AppCompatActivity implements Observer {

    @Inject
    MealPlanController controller;

    private EditText planName;
    private EditText planStart;
    private EditText planEnd;

    private class EventListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.finish_new_meal_step1_button) {
                // Request controller to create a new Meal Plan
                // Once a new Meal Plan is created and added in the Repository
                // update() method will be called to launch an new Activity

                controller.add(
                        // TODO: add input verification
                        planName.getText().toString(),
                        DateUtil.getCalendar(planStart.getText().toString()).getTime(),
                        DateUtil.getCalendar(planEnd.getText().toString()).getTime()
                );

            } else if (v.getId() == R.id.cancel_new_meal_plan_button) {

                controller.deleteObserver(NewMealPlanActivity.this);

                Intent intent = new Intent(NewMealPlanActivity.this,
                        MainActivity.class);

                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Nosh) getApplicationContext()).getAppComponent().inject(this);

        controller.addObserver(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal_plan);

        EventListener listener = new EventListener();

        planName = findViewById(R.id.new_meal_plan_name);
        planStart = findViewById(R.id.new_meal_plan_start);
        planEnd = findViewById(R.id.new_meal_plan_end);

        Button nextStepButton = findViewById(R.id.finish_new_meal_step1_button);
        Button cancelButton = findViewById(R.id.cancel_new_meal_plan_button);

        nextStepButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
    }

    private void launchAddMealsToDaysActivity(String mealPlanHashcode) {
        Intent intent = new Intent(this, AddMealsToDaysActivity.class);

        intent.putExtra(MealPlanRepository.MEAL_PLAN_HASHCODE, mealPlanHashcode);

        startActivity(intent);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Transaction) {
            Transaction transaction = (Transaction) arg;

            if (transaction.getTag()
                    .compareTo(MealPlanRepository.CREATE_NEW_MEAL_PLAN) == 0) {

                controller.deleteObserver(this);

                launchAddMealsToDaysActivity(
                        (String) transaction
                                .getContents()
                                .get(MealPlanRepository.MEAL_PLAN_HASHCODE)
                );
            }
        }
    }
}