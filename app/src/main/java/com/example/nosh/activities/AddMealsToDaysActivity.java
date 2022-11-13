package com.example.nosh.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nosh.R;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealPlan;

/**
 * This activity allows the user to define a number of meals for each day of the meal plan,
 * for every day the user can add as many meals as he wants. When done with a specific day, he can
 * proceed to the next and repeat the process. Once the user as gone through all the days or cancels
 * this activity will end.
 */
public class AddMealsToDaysActivity extends AppCompatActivity {
    private TextView currentDay;
    private EditText mealName;
    private EditText mealServings;
    private ListView foodStuff;
    private ArrayAdapter<String> adapter;
    private ImageButton newMealButton;
    private ImageButton previousMealButton;
    private Button nextPlanDayButton;

    private Integer dayCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meals_to_days);

        Bundle extras = getIntent().getExtras();
        MealPlan mealPlan = (MealPlan) extras.getSerializable("meal_plan");

        currentDay = findViewById(R.id.current_plan_day);
        mealName = findViewById(R.id.new_meal_name);
        mealServings = findViewById(R.id.new_meal_servings);
        newMealButton = findViewById(R.id.new_meal_button);
        previousMealButton = findViewById(R.id.previous_meal_button);
        nextPlanDayButton  =  findViewById(R.id.finish_adding_meals_button);

        // TODO: this should be populated from the actual list of recipes and ingredients
        String[] stuff = {"Ice cream", "Hot Dogs", "Pizza", "Apple"};
        foodStuff = findViewById(R.id.foodStuff_selection_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, stuff);
        foodStuff.setAdapter(adapter);

        // the day displayed at the top
        currentDay.setText("Day " + dayCount);

        // button listeners

        previousMealButton.setOnClickListener(view -> {
            clearInput();
        });

        newMealButton.setOnClickListener(view -> {
            createMeal();
            clearInput();
        });

        nextPlanDayButton.setOnClickListener(view -> {
            dayCount++;
            currentDay.setText("Day " + dayCount);

            createMeal();
            clearInput();
        });
    }

    /**
     * Creates a new meal with all the user input and adds it to its meal plan
     */
    void createMeal(){
        //Meal newMeal = new Meal(mealName.getText().toString(), Integer.parseInt(mealServings.getText().toString()));
        for (int i = 0; i < foodStuff.getCount(); i++){
            if (foodStuff.isItemChecked(i)){
                // TODO: the selected meal components should be added to the meal
                //newMeal.addMealComponent(foodStuff.getItemAtPosition(0));
            }
        }

        // TODO: call update on the meal in the database
    }

    void clearInput(){
        mealName.setText(" ");
        mealServings.setText(" ");
        foodStuff.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}