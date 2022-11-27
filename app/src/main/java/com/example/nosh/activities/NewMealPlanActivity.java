package com.example.nosh.activities;

import static com.example.nosh.controller.MealPlanController.CREATE_NEW_MEAL_PLAN;
import static com.example.nosh.controller.MealPlanController.MEAL_PLAN_HASHCODE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nosh.MainActivity;
import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.entity.Transaction;
import com.example.nosh.utils.DateUtil;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;


/**
 * This activity handles the creation of a new meal plan, it will register the new meal plan in
 * firebase and pass it to the next activity where meals will be defined
 */
public class NewMealPlanActivity extends AppCompatActivity implements Observer,
        DatePickerDialog.OnDateSetListener {

    @Inject
    MealPlanController controller;

    private EditText planName;
    private Button planStart;
    private Button planEnd;
    DatePickerDialog datePickerDialog;
    private Boolean selectingStartDate = false;
    private Boolean selectingEndDate = false;

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

                Intent intent = new Intent(NewMealPlanActivity.this,
                        MainActivity.class);

                startActivity(intent);
            } else if (v.getId() == R.id.new_meal_plan_start){
                selectingStartDate = true;
                selectingEndDate = false;
                datePickerDialog.show();
            } else if (v.getId() == R.id.new_meal_plan_end){
                selectingStartDate = false;
                selectingEndDate = true;
                datePickerDialog.show();
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


        // DatePickerDialog initialization
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, this, year,
                month, day);

        planName = findViewById(R.id.new_meal_plan_name);
        planStart = findViewById(R.id.new_meal_plan_start);
        planEnd = findViewById(R.id.new_meal_plan_end);

        Button nextStepButton = findViewById(R.id.finish_new_meal_step1_button);
        ImageButton cancelButton = findViewById(R.id.cancel_new_meal_plan_button);

        nextStepButton.setOnClickListener(eventListener);
        cancelButton.setOnClickListener(eventListener);
        planStart.setOnClickListener(eventListener);
        planEnd.setOnClickListener(eventListener);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        int displayMonth = month + 1;
        if (Integer.toString(month).length() == 1 && Integer.toString(day).length() == 1) {
            if (selectingStartDate){
                planStart.setText(year + "-" + "0" + displayMonth + "-" + "0" + day);
            } else {
                planEnd.setText(year + "-" + "0" + displayMonth + "-" + "0" + day);
            }
            return;
        }
        
        if (Integer.toString(month).length() == 1) {
            if (selectingStartDate){
                planStart.setText(year + "-" + "0" + displayMonth + "-" + day);
            } else {
                planEnd.setText(year + "-" + "0" + displayMonth + "-" + day);
            }
            return;
        }
        
        if (Integer.toString(day).length() == 1) {
            if (selectingStartDate) {
                planStart.setText(year + "-" + displayMonth + "-" + "0" + day);
            } else {
                planEnd.setText(year + "-" + displayMonth + "-" + "0" + day);
            }
            return;
        }

        if (selectingStartDate) {
            planStart.setText(year + "-" + displayMonth + "-" + day);
        } else {
            planEnd.setText(year + "-" + displayMonth + "-" + day);
        }
    }
}
