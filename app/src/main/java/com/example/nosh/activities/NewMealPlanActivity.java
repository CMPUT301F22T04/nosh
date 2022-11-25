package com.example.nosh.activities;

import static com.example.nosh.controller.MealPlanController.CREATE_NEW_MEAL_PLAN;
import static com.example.nosh.controller.MealPlanController.MEAL_PLAN_HASHCODE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.entity.Transaction;
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
                cancelOperation();
            }
        }
    }

    @Override
    public void onBackPressed() {
        cancelOperation();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Nosh) getApplicationContext()).getAppComponent().inject(this);

        controller.addObserver(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal_plan);

        EventListener eventListener = new EventListener();


        planName = findViewById(R.id.new_meal_plan_name);
        planStart = findViewById(R.id.new_meal_plan_start);
        planEnd = findViewById(R.id.new_meal_plan_end);

        Button nextStepButton = findViewById(R.id.finish_new_meal_step1_button);
        Button cancelButton = findViewById(R.id.cancel_new_meal_plan_button);

        nextStepButton.setOnClickListener(eventListener);
        cancelButton.setOnClickListener(eventListener);
    }

    @Override
    protected void onDestroy() {
        controller.deleteObserver(this);

        super.onDestroy();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Transaction) {
            Transaction transaction = (Transaction) arg;

            if (transaction.getTag()
                    .compareTo(CREATE_NEW_MEAL_PLAN) == 0) {

                launchAddMealsToDaysActivity(
                        (String) transaction
                                .getContents()
                                .get(MEAL_PLAN_HASHCODE)
                );
            }
        }
    }

    private void cancelOperation() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void launchAddMealsToDaysActivity(String mealPlanHashcode) {
        controller.deleteObserver(this);

        Intent intent = new Intent(this, AddMealsToDaysActivity.class);

        intent.putExtra(MEAL_PLAN_HASHCODE, mealPlanHashcode);

        startActivity(intent);
        finish();
    }
}