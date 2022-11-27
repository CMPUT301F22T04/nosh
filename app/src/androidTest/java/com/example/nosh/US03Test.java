package com.example.nosh;

import android.widget.DatePicker;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.nosh.auth.Login;
import com.example.nosh.auth.Register;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.Assert;

/**
 * This class is responsible for intent tests of all US 03. (Please run US 02 tests first)
 * @author Lok Him Isaac Cheng
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class US03Test {
    // Testing variables declaration
    private Solo solo;
    private final int pause = 300;

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

        // Switch to Meal Plan tab
        solo.clickOnText("Meal Plan");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause * 2);
    }

    /**
     * US 03.01.01 Test
     * This test if the app can make a meal plan with current recipe or ingredient storage.
     */
    @Test
    public void US030101Test(){
        // Step 1
        String planName = "Healthy Plan";
        int year1 = 2022, month1 = 12, day1 = 3;
        int year2 = 2022, month2 = 12, day2 = 5;
        solo.clickOnView(solo.getView(R.id.new_meal_plan_button));
        solo.enterText((EditText) solo.getView(R.id.new_meal_plan_name), planName);
        solo.waitForActivity("Timeout", pause * 10);
        setDate(year1, month1, day1);
        setDate(year2, month2, day2);
        solo.clickOnView(solo.getView(R.id.finish_new_meal_step1_button));
        solo.waitForActivity("Timeout", pause * 20);

        // Step 2 - Day 1
        String mealName1 = "Spaghetti Meal";
        int mealServing1 = 2;
        solo.enterText((EditText) solo.getView(R.id.new_meal_name), mealName1);
        solo.waitForActivity("Timeout", pause * 2);
        solo.enterText((EditText) solo.getView(R.id.new_meal_servings),
                String.valueOf(mealServing1));
        solo.waitForActivity("Timeout", pause * 2);
        solo.clickOnText("Tomato Sauce Spaghetti");
        solo.clickOnView(solo.getView(R.id.finish_adding_meals_button));
        solo.waitForActivity("Timeout", pause * 10);

        // Step 2 - Day 2
        String mealName2 = "Yummy Sandwich";
        int mealServing2 = 1;
        solo.clearEditText((EditText) solo.getView(R.id.new_meal_name));
        solo.waitForActivity("Timeout", pause * 2);
        solo.enterText((EditText) solo.getView(R.id.new_meal_name), mealName2);
        solo.waitForActivity("Timeout", pause * 2);
        solo.clearEditText((EditText) solo.getView(R.id.new_meal_servings));
        solo.waitForActivity("Timeout", pause * 2);
        solo.enterText((EditText) solo.getView(R.id.new_meal_servings),
                String.valueOf(mealServing2));
        solo.waitForActivity("Timeout", pause * 2);
        solo.clickOnText("Tomato Sauce Spaghetti");
        solo.clickOnText("Tuna Fish Sandwich");
        solo.clickOnView(solo.getView(R.id.finish_adding_meals_button));
        solo.waitForActivity("Timeout", pause * 5);

        // Step 2 - Day 3
        String mealName3 = "Spaghetti and Pie";
        int mealServing3 = 3;
        solo.clearEditText((EditText) solo.getView(R.id.new_meal_name));
        solo.waitForActivity("Timeout", pause * 2);
        solo.enterText((EditText) solo.getView(R.id.new_meal_name), mealName3);
        solo.waitForActivity("Timeout", pause * 2);
        solo.clearEditText((EditText) solo.getView(R.id.new_meal_servings));
        solo.waitForActivity("Timeout", pause * 2);
        solo.enterText((EditText) solo.getView(R.id.new_meal_servings),
                String.valueOf(mealServing3));
        solo.waitForActivity("Timeout", pause * 2);
        solo.clickOnText("Tomato Sauce Spaghetti");
        solo.clickOnText("Blueberry Pie");
        solo.clickOnText("Tuna Fish Sandwich");
        solo.clickOnView(solo.getView(R.id.finish_adding_meals_button));
        solo.waitForActivity("Timeout", pause * 10);
    }

    /**
     * US 03.01.01 Test
     * This test if the app can make a meal plan with current recipe or ingredient storage.
     */
    @Test
    public void US030201Test(){



    }

    public void setDate(int year, int month, int day){
        solo.clickOnButton("yyyy-mm-dd");
        solo.setDatePicker(0, year, month, day);
        solo.clickOnText("OK");
        solo.waitForActivity("Timeout", pause * 10);
    }

    @After
    public void tearDown(){
        solo.finishOpenedActivities();
    }
}
