package com.example.nosh.activities;

import static com.example.nosh.controller.MealPlanController.ADD_MEAL_TO_DAY;
import static com.example.nosh.controller.MealPlanController.MEAL_PLAN_HASHCODE;

import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nosh.MainActivity;
import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.IngredientStorageController;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.controller.RecipeController;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.Transaction;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

/**
 * This activity allows the user to define a number of meals for each day of the meal plan,
 * for every day the user can add as many meals as he wants. When done with a specific day, he can
 * proceed to the next and repeat the process. Once the user as gone through all the days or cancels
 * this activity will end.
 */
public class AddMealsToDaysActivity extends AppCompatActivity implements Observer {

    @Inject
    IngredientStorageController ingredientStorageController;

    @Inject
    RecipeController recipeController;

    @Inject
    MealPlanController mealPlanController;

    private TextView currentDay;
    private EditText mealName;
    private EditText mealServings;
    private ListView mealComponentListView;
    private ArrayAdapter<String> adapter;

    private MealPlan mealPlan;
    private final ArrayList<MealComponent> mealComponents = new ArrayList<>();
    private final ArrayList<String> mealComponentsName = new ArrayList<>();

    private EventListener listener;

    private Integer dayCount = 1;

    private class EventListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.finish_adding_meals_button) {
                dayCount += 1;

                if (dayCount > mealPlan.getTotalDays()){
                    finishOperation();
                }

                String nextDayText = "Day " + dayCount;
                currentDay.setText(nextDayText);
                Toast.makeText(getApplicationContext(), "Next day",
                        Toast.LENGTH_SHORT).show();


            } else if (v.getId() == R.id.previous_meal_button) {
                if (dayCount - 1 > 0) {
                    dayCount -= 1;

                    String previousDayText = "Day " + dayCount;
                    currentDay.setText(previousDayText);
                    Toast.makeText(getApplicationContext(), "Previous day",
                            Toast.LENGTH_SHORT).show();

                    clearInput();
                }
            } else if (v.getId() == R.id.new_meal_button) {
                createMeal();
                Toast.makeText(getApplicationContext(), "New Meal added to day " + dayCount,
                        Toast.LENGTH_SHORT).show();
                clearInput();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((Nosh) getApplicationContext()).getAppComponent().inject(this);

        mealPlanController.addObserver(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meals_to_days);

        Bundle extras = getIntent().getExtras();
        String hashcode = extras.getString(MEAL_PLAN_HASHCODE);

        listener = new EventListener();

        mealPlan = mealPlanController.retrieve(hashcode);

        mealComponents.addAll(ingredientStorageController.retrieve());
        mealComponents.addAll(recipeController.retrieve());

        for (MealComponent mealComment :
                mealComponents) {
            mealComponentsName.add(mealComment.getName());
        }

        initializeView();
    }

    @Override
    public void onBackPressed() {
        finishOperation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        mealPlanController.deleteObserver(this);

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable o, Object arg) {
        assert arg instanceof Transaction;

        Transaction transaction = (Transaction) arg;
        if (transaction.getTag().equals(ADD_MEAL_TO_DAY)) {
            String hashcode = (String) transaction.getContents().get(MEAL_PLAN_HASHCODE);

            assert Objects.requireNonNull(hashcode).contentEquals(mealPlan.getHashcode());

            mealPlan = mealPlanController.retrieve(hashcode);
        }
    }

    /**
     * Creates a new meal with all the user input and adds it to its meal plan
     */
    void createMeal(){
        // TODO: call update on the meal in the database

        // TODO: input verification
        Meal meal = new Meal(
                Long.parseLong(mealServings.getText().toString()),
                "",
                mealPlan.getHashcode(),
                mealName.getText().toString()
        );

        long serving = Long.parseLong(mealServings.getText().toString());

        for (int i = 0; i < mealComponentListView.getCount(); i++){
            if (mealComponentListView.isItemChecked(i)) {
                meal.addMealComponent(mealComponents.get(i));
            }
        }

        mealPlanController.addMealToDay(
                mealPlan.getStartDate(),
                dayCount,
                meal,
                mealPlan.getHashcode());
    }

    void clearInput(){
        mealName.setText("");
        mealServings.setText("");
        mealComponentListView.setAdapter(adapter);
    }

    private void finishOperation() {
        setResult(RESULT_OK);

        Intent intent = new Intent(this, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        startActivity(intent);
        finish();
    }

    private void initializeView() {
        currentDay = findViewById(R.id.current_plan_day);
        mealName = findViewById(R.id.new_meal_name);
        mealServings = findViewById(R.id.new_meal_servings);
        ImageButton newMealButton = findViewById(R.id.new_meal_button);
        ImageButton previousMealButton = findViewById(R.id.previous_meal_button);
        Button nextPlanDayButton = findViewById(R.id.finish_adding_meals_button);

        mealComponentListView = findViewById(R.id.foodStuff_selection_view);
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                mealComponentsName);

        mealComponentListView.setAdapter(adapter);

        // the day displayed at the top
        String dayText = "Day " + dayCount;
        currentDay.setText(dayText);

        // button listener
        previousMealButton.setOnClickListener(listener);
        newMealButton.setOnClickListener(listener);
        nextPlanDayButton.setOnClickListener(listener);
    }
}
