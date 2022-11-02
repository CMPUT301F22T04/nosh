package com.example.nosh;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import com.example.nosh.auth.Login;
import com.example.nosh.auth.Register;
import com.example.nosh.fragments.ingredients.IngredientsFragment;
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


    /*Establishes test rules*/
    @Rule
    public ActivityTestRule<Register> rule =
            new ActivityTestRule<>(Register.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{

        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        Activity activity = rule.getActivity();
    }

    @Test
    public void test1LoginTest(){
        int seconds = 1000;

        String name = "Spaghetti";
        String description = "A long, thin pasta.";
        String location = "Cabinet";
        int quantity = 500;
        int unit = 1;
        String category = "Pasta";

        String locationEdit = "Jar";
        int quantityEdit = 400;

        // Check if current activity is Register
        solo.assertCurrentActivity("Wrong activity", Register.class);
        solo.waitForActivity("Timeout", seconds);

        // Go to Login activity and then check
        solo.clickOnText("Login");
        solo.assertCurrentActivity("Wrong activity", Login.class);
        solo.waitForActivity("Timeout", seconds);

        // Login
        String loginEmail = "isaac.cheng97@gmail.com";
        String loginPassword = "test123";
        solo.enterText((EditText) solo.getView(R.id.login_email), loginEmail);
        solo.enterText((EditText) solo.getView(R.id.login_password), loginPassword);
        solo.clickOnButton("Login");
        solo.waitForActivity("Timeout", seconds);

        // US 01.01.01 - add ingredient
        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", seconds);
        solo.clickOnView(solo.getView("add_btn"));
        solo.waitForActivity("Timeout", seconds);
        solo.enterText((EditText) solo.getView(R.id.add_name), name);
        solo.waitForActivity("Timeout", seconds);
        solo.enterText((EditText) solo.getView(R.id.add_description), description);
        solo.waitForActivity("Timeout", seconds);
        solo.clickOnButton("yyyy-mm-dd");
        solo.setDatePicker(0, 2022, 11, 26);
        solo.clickOnText("OK");
        solo.waitForActivity("Timeout", seconds);
        solo.enterText((EditText) solo.getView(R.id.add_storage_location), location);
        solo.waitForActivity("Timeout", seconds);
        solo.enterText((EditText) solo.getView(R.id.add_qty), String.valueOf(quantity));
        solo.waitForActivity("Timeout", seconds);
        solo.enterText((EditText) solo.getView(R.id.add_unit), String.valueOf(unit));
        solo.waitForActivity("Timeout", seconds);
        solo.enterText((EditText) solo.getView(R.id.add_ingredient_category), category);
        solo.waitForActivity("Timeout", seconds);
        solo.clickOnButton("Add");
        solo.waitForActivity("Timeout", seconds);

        // US 01.02.01 - view ingredient
        solo.clickOnText(name);
        solo.waitForActivity("Timeout", seconds * 5);
        solo.goBack();
        solo.waitForActivity("Timeout", seconds);

        // US 01.03.01 - edit ingredient
        solo.clickOnText(name);
        solo.clearEditText((EditText) solo.getView(R.id.edit_storage_location));
        solo.enterText((EditText) solo.getView(R.id.edit_storage_location), locationEdit);
        solo.waitForActivity("Timeout", seconds);
        solo.clearEditText((EditText) solo.getView(R.id.edit_qty));
        solo.enterText((EditText) solo.getView(R.id.edit_qty), String.valueOf(quantityEdit));
        solo.waitForActivity("Timeout", seconds);
        solo.clickOnButton("Confirm");
        solo.waitForActivity("Timeout", seconds);

        // US 01.04.01 - delete ingredient
        solo.clickOnView(solo.getView("del_btn"));
        solo.waitForActivity("Timeout", seconds);

        //US 01.05.01 - view list of ingredient
        solo.waitForActivity("Timeout", seconds * 10);


    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}