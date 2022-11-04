package com.example.nosh;

import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.nosh.auth.Login;
import com.example.nosh.auth.Register;
import com.example.nosh.entity.Ingredient;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class US02Test {
    // Testing variables declaration
    private Solo solo;
    private final int pause = 200;

    // Sample Recipe Information
    String title = "";
    int time;
    int servings;
    String category;
    String comments;
    ArrayList<Ingredient> ingredients;

    // Establishes test rules
    @Rule
    public ActivityTestRule<Register> rule =
            new ActivityTestRule<>(Register.class, true, true);

    /**
     * Goes to the login page and login with credentials and switch to recipe tab
     */
    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

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

        // Switch to Recipe tab
        solo.clickOnText("Recipe");
        solo.waitForActivity("Timeout", pause * 2);
    }

    /**
     * US 02.01.01 Test
     * This test if the app adds a recipe successfully.
     */
    @Test
    public void US020101Test(){
    }

    /**
     * US 02.02.01 Test
     * This test if the app adds an ingredient to a recipe successfully.
     */
    @Test
    public void US020201Test(){
    }

    /**
     * US 02.03.01 Test
     * This test if the app deletes an ingredient from a recipe successfully.
     */
    @Test
    public void US020301Test(){
    }

    /*
    @Test
    public void US020401Test(){
    }
    @Test
    public void US020501Test(){
    }
    */

    /**
     * US 02.06.01 Test
     * This test if the app deletes a recipe successfully.
     */
    @Test
    public void US020601Test(){
    }

    /**
     * US 02.07.01 Test
     * This test checks if a list of recipe is displayed correctly.
     */
    @Test
    public void US020701Test(){
    }

    /**
     * US 02.08.01 Test
     * This test if recipe sorted correctly
     */
    @Test
    public void US020801Test(){
    }

    @After
    public void tearDown(){
        solo.finishOpenedActivities();
    }
}
