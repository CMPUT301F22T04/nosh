package com.example.nosh.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nosh.R;
import com.example.nosh.entity.MealPlan;

public class AddMealsToDaysActivity extends AppCompatActivity {
    private MealPlan mealPlan;
    private ListView foodStuff;
    ArrayAdapter<String> adapter;
    String[] stuff = {"Ice cream", "Hot Dogs", "Pizza", "Apple"};

    private ImageButton nextDayButton;
    private ImageButton previousDayButton;
    private TextView currentDay;

    private Integer day = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meals_to_days);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mealPlan = (MealPlan) extras.getSerializable("meal_plan");
        }

        nextDayButton = findViewById(R.id.next_day_button);
        previousDayButton = findViewById(R.id.previous_day_button);
        currentDay = findViewById(R.id.current_day);

        foodStuff = findViewById(R.id.foodStuff_selection_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, stuff);
        foodStuff.setAdapter(adapter);

        // the day displayed at the top
        currentDay.setText(day.toString());

        Button testButton =  findViewById(R.id.testing_selection_button);
        testButton.setOnClickListener(view -> foodStuff.setAdapter(adapter));

        previousDayButton.setOnClickListener(view -> {
            day--;
            currentDay.setText(day.toString());
            foodStuff.setAdapter(adapter);
        });

        nextDayButton.setOnClickListener(view -> {
            day++;
            currentDay.setText(day.toString());
            foodStuff.setAdapter(adapter);
        });
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

// iterate the whole list of foodStuff and set everything that is already in this meal to checked
//foodStuff.setItemChecked(0, true);