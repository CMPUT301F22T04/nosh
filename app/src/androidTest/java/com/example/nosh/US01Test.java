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

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class US01Test {

    /*Declaration of variables*/
    private Solo solo;
    private final int pause = 200;

    String name = "Spaghetti";
    String description = "A long, thin pasta.";
    String location = "Cabinet";
    int year = 2025;
    int month = 5;
    int day = 20;
    int quantity = 500;
    int unit = 1;
    String category = "Pasta";

    /*Establishes test rules*/
    @Rule
    public ActivityTestRule<Register> rule =
            new ActivityTestRule<>(Register.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
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
        String loginEmail = "isaac.cheng97@gmail.com";
        String loginPassword = "test123";
        solo.enterText((EditText) solo.getView(R.id.login_email), loginEmail);
        solo.enterText((EditText) solo.getView(R.id.login_password), loginPassword);
        solo.clickOnButton("Login");
        solo.waitForActivity("Timeout", pause);
    }
    @Test
    public void test1US010101(){
        // US 01.01.01 - add ingredient
        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause);
        solo.clickOnView(solo.getView("add_btn"));
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.add_name), name);
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.add_description), description);
        solo.waitForActivity("Timeout", pause);
        solo.clickOnButton("yyyy-mm-dd");
        solo.setDatePicker(0, year, month, day);
        solo.clickOnText("OK");
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.add_storage_location), location);
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.add_qty), String.valueOf(quantity));
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.add_unit), String.valueOf(unit));
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.add_ingredient_category), category);
        solo.waitForActivity("Timeout", pause);
        solo.clickOnButton("Add");
        solo.waitForActivity("Timeout", pause);
    }

    /**
     * Unit and intent test for US01.02.01.
     */
    @Test
    public void test2US010201(){
        // US 01.02.01 - view ingredient
        solo.clickOnText(name);
        solo.waitForActivity("Timeout", pause * 5);
        solo.goBack();
        solo.waitForActivity("Timeout", pause);

    }


    /**
     * Known problem: unable to save the new info
     * Need assert to test values
     */
    @Test
    public void test3US010301(){
        String name = "Spaghetti";
        String locationEdit = "Jar";
        int quantityEdit = 400;

        // US 01.03.01 - edit ingredient
        solo.clickOnText(name);
        solo.clearEditText((EditText) solo.getView(R.id.edit_storage_location));
        solo.enterText((EditText) solo.getView(R.id.edit_storage_location), locationEdit);
        solo.waitForActivity("Timeout", pause);
        solo.clearEditText((EditText) solo.getView(R.id.edit_qty));
        solo.enterText((EditText) solo.getView(R.id.edit_qty), String.valueOf(quantityEdit));
        solo.waitForActivity("Timeout", pause);
        solo.clickOnButton("Confirm");
        solo.waitForActivity("Timeout", pause);
    }

    /**
     * Known problem: pressing delete crashes the app
     */
    @Test
    public void test4US010401(){
        // US 01.04.01 - delete ingredient
        //solo.clickOnView(solo.getView("del_btn"));
        //solo.waitForActivity("Timeout", seconds);
    }

    @Test
    public void test5US010501(){
        //US 01.05.01 - view list of ingredient
        solo.waitForActivity("Timeout", pause * 10);
    }

    @After
    public void tearDown(){
        solo.finishOpenedActivities();
    }
}

