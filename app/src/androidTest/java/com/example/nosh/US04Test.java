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

/**
 * This class is responsible for intent tests of all US 04.
 * @author Lok Him Isaac Cheng
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class US04Test {
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

        // Switch to Shopping List tab
        solo.clickOnText("Shopping List");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause * 2);
    }

    /**
     * US 04.01.01 Test
     * This test if the app display correct items to be bought based on recipe and ingredient
     * storage
     */
    @Test
    public void US040101Test(){
        // Nothing to be done by robotium
    }

    /**
     * US 04.02.01 Test
     * This test if the app display description, amount, unit, and ingredient category of each
     * item in the shopping list
     */
    @Test
    public void US040201Test(){
        // Nothing to be done by robotium
    }

    /**
     * US 04.03.01 Test
     * This test if the app can sort the shopping list of ingredients by description or category.
     */
    @Test
    public void US040301Test(){
        sortShoppingList(0);
        sortShoppingList(1);
        solo.waitForActivity("Timeout", pause * 15);
    }

    /**
     * US 04.04.01 Test
     * This test if the app can note that the user have picked up an ingredient on the shopping
     * list.
     */
    @Test
    public void US040401Test(){

    }

    /**
     * US 04.05.01 Test
     * This test if the app can add ingredients I have picked up to my ingredient storage,
     * reminding me to complete details like location, actual amount, and unit.
     */
    @Test
    public void US040501Test(){

    }

    public void sortShoppingList(int criteria){
        // Press sort button
        solo.clickOnView(solo.getView(R.id.sort_list_button));
        solo.waitForActivity("Timeout", pause);

        // Pick criteria
        switch(criteria) {
            case 0:
                solo.clickOnText("Description");
                break;
            case 1:
                solo.clickOnText("Category");
                break;
        }

        // Press sort button
        solo.clickOnView(solo.getView("confirm_sortL"));
        solo.waitForActivity("Timeout", pause * 10);
    }

    @After
    public void tearDown(){
        solo.finishOpenedActivities();
    }
}
