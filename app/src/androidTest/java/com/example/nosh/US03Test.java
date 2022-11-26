package com.example.nosh;

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

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class US03Test {
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
        /* Create meal plan
        String planName = "Healthy Plan";
        solo.clickOnText("New Meal Plan");
        solo.enterText((EditText) solo.getView(R.id.new_meal_plan_name), planName);
         */


    }

    /**
     * US 03.01.01 Test
     * This test if the app can make a meal plan with current recipe or ingredient storage.
     */
    @Test
    public void US030201Test(){
        // Check if current activity is MainActivity


    }
}
