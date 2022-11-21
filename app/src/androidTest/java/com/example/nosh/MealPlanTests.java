package com.example.nosh;

import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.nosh.auth.Login;
import com.example.nosh.auth.Register;
import com.robotium.solo.Solo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MealPlanTests {
    // Testing variables declaration
    private Solo solo;
    private final int pause = 100;

    // Establishes test rules
    @Rule
    public ActivityTestRule<Register> rule =
            new ActivityTestRule<>(Register.class, true, true);

    /**
     * Goes to the login page and login with credentials
     */
    @Before
    public void setUp(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

        // Check if current activity is Register
        solo.assertCurrentActivity("Wrong activity", Register.class);
        solo.waitForActivity("Timeout", pause);

        // Go to Login activity and then check
        solo.clickOnText("Login");
        solo.assertCurrentActivity("Wrong activity", Login.class);
        solo.waitForActivity("Timeout", pause);

        // Login
        String loginEmail = "testing@gmail.com";
        String loginPassword = "password12345";
        solo.enterText((EditText) solo.getView(R.id.login_email), loginEmail);
        solo.enterText((EditText) solo.getView(R.id.login_password), loginPassword);
        solo.clickOnButton("Login");
        solo.waitForActivity("Timeout", pause);

        // Switch to Meal plan tab
        solo.clickOnText("Meal Plan");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause * 2);
    }

    /**
     * New meal plan
     * This test if the app adds a new meal plan successfully
     */
    @Test
    public void newMealPlan(){
        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause);

        // Click new meal plan button
        solo.clickOnView(solo.getView("new_meal_plan_button"));
        solo.waitForActivity("Timeout", pause);

        createMealPlan("Vegan Diet", "2000-02-11", "2000-02-14");

        // Confirm new meal plan
        solo.clickOnButton("Create");
        solo.waitForActivity("Timeout", pause * 2);
    }

    @Test
    public void cancelNewMealPlan(){
        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause);

        // Click new meal plan button
        solo.clickOnView(solo.getView("new_meal_plan_button"));
        solo.waitForActivity("Timeout", pause);

        createMealPlan("Mom's recipes", "2000-07-11", "2000-07-24");

        // Cancel new meal plan
        solo.clickOnButton("Cancel");
        solo.waitForActivity("Timeout", pause * 2);
    }

    @Test
    public void addMealsToDays(){
        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause);

        // Click new meal plan button
        solo.clickOnView(solo.getView("new_meal_plan_button"));
        solo.waitForActivity("Timeout", pause);

        createMealPlan("Vegan Diet", "2000-02-11", "2000-02-14");

        // Confirm new meal plan
        solo.clickOnButton("Create");
        solo.waitForActivity("Timeout", pause * 2);

        // Add meals to day 1
        solo.enterText((EditText) solo.getView(R.id.new_meal_name), "Noodle soup");
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.new_meal_servings), "1");
        solo.waitForActivity("Timeout", pause);

        // TODO: select some recipes or ingredients when done

        solo.clickOnView(solo.getView("new_meal_button"));
        solo.waitForActivity("Timeout", pause);

        solo.enterText((EditText) solo.getView(R.id.new_meal_name), "Ratatouille");
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.new_meal_servings), "3");
        solo.waitForActivity("Timeout", pause);

        solo.clickOnView(solo.getView("finish_adding_meals_button"));
        solo.waitForActivity("Timeout", pause);

        // Add meals to day 2
        solo.enterText((EditText) solo.getView(R.id.new_meal_name), "British breakfast");
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.new_meal_servings), "1");
        solo.waitForActivity("Timeout", pause);

        // TODO: select some recipes or ingredients when done

        solo.clickOnView(solo.getView("finish_adding_meals_button"));
        solo.waitForActivity("Timeout", pause);

        // Add meals to day 3
        solo.enterText((EditText) solo.getView(R.id.new_meal_name), "Rice cakes");
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.new_meal_servings), "4");
        solo.waitForActivity("Timeout", pause);

        // TODO: select some recipes or ingredients when done

        solo.clickOnView(solo.getView("finish_adding_meals_button"));
        solo.waitForActivity("Timeout", pause);

        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause);
    }

    @Test
    public void checkOutMealPlan(){
        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause);

        // Click new meal plan button
        solo.clickOnView(solo.getView("new_meal_plan_button"));
        solo.waitForActivity("Timeout", pause);

        createMealPlan("Uni Meal Prep", "2000-02-11", "2000-02-12");

        // Confirm new meal plan
        solo.clickOnButton("Create");
        solo.waitForActivity("Timeout", pause * 2);

        solo.clickOnView(solo.getView("finish_adding_meals_button"));
        solo.waitForActivity("Timeout", pause);

        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause);

        // Click new meal plan button
        solo.clickOnText("Meal Plan");
        solo.waitForActivity("Timeout", pause);

        Assert.assertTrue("Search for new Meal plan", solo.searchText("Uni Meal Prep"));

        solo.clickOnText("Uni Meal Prep");
        solo.waitForActivity("Timeout", pause);

        //TODO: Verify that the meals all match
    }

    public void createMealPlan(String name, String start, String end){
        // Enter new meal plan information
        solo.enterText((EditText) solo.getView(R.id.new_meal_plan_name), name);
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.new_meal_plan_start), start);
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.new_meal_plan_end), end);
        solo.waitForActivity("Timeout", pause);
    }
}
