package com.example.nosh;

import android.widget.Button;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.nosh.auth.Login;
import com.example.nosh.auth.Register;
import com.example.nosh.entity.Ingredient;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
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

    // Sample Recipe (Tomato Sauce Spaghetti) Information
    String title1 = "Tomato Sauce Spaghetti";
    int time1 = 30;
    int servings1 = 2;
    String category1 = "Pasta";
    String comments1 = "A tasty pasta for everyone.";

    // Sample Recipe (Tuna Fish Sandwich) Information
    String title2 = "Tuna Fish Sandwich";
    int time2 = 15;
    int servings2 = 1;
    String category2 = "Sandwich";
    String comments2 = "A sandwich full of protein and vitamin.";

    // Sample Recipe (Blueberry Pie) Information
    String title3 = "Blueberry Pie";
    int time3 = 20;
    int servings3 = 4;
    String category3 = "Pastry";
    String comments3 = "Juicy blueberries in a golden crust.";

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
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause * 2);
    }

    /**
     * US 02.01.01 Test
     * This test if the app adds a recipe successfully.
     * Note: as robotium cannot control activities outside of this application, image adding
     * cannot be tested
     */
    @Test
    public void US020101Test(){
        addRecipe(title1, time1, servings1, category1, comments1);
    }

    /**
     * US 02.02.01 Test
     * This test if the app adds an ingredient to a recipe successfully.
     */
    //@Test
    public void US020201Test(){

    }

    /**
     * US 02.03.01 Test
     * This test if the app deletes an ingredient from a recipe successfully.
     */
    //@Test
    public void US020301Test(){
    }


    /**
     * US 02.04.01 Test
     * This test if the app view details properly.
     */
    //@Test
    public void US020401Test(){
    }

    /**
     * US 02.05.01 Test
     * This test if the app edit details properly.
     */
    //@Test
    public void US020501Test(){
    }


    /**
     * US 02.06.01 Test
     * This test if the app deletes a recipe successfully.
     */
    //@Test
    public void US020601Test(){

    }

    /**
     * US 02.07.01 Test
     * This test checks if a list of recipe is displayed correctly.
     */
    @Test
    public void US020701Test(){
        addRecipe(title2, time2, servings2, category2, comments2);
        addRecipe(title3, time3, servings3, category3, comments3);

        // Checks if the ingredients are in the list
        Assert.assertTrue("Search for Ingredient 1", solo.searchText(title1));
        Assert.assertTrue("Search for Ingredient 2", solo.searchText(title2));
        Assert.assertTrue("Search for Ingredient 3", solo.searchText(title3));
        solo.waitForActivity("Timeout", pause * 10);
    }

    /**
     * US 02.08.01 Test
     * This test if recipe sorted correctly.
     */
    //@Test
    public void US020801Test(){

    }

    public void addRecipe(String title, int time, int servings, String category, String comments){
        // Press add button
        solo.clickOnView(solo.getView("add_recipe_btn"));
        solo.waitForActivity("Timeout", pause);

        // Enter recipe info
        solo.enterText((EditText) solo.getView(R.id.recipe_name_field), title);
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.recipe_category_field), category);
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.serving_field), String.valueOf(servings));
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.preparation_time_field), String.valueOf(time));
        solo.waitForActivity("Timeout", pause);
        solo.enterText((EditText) solo.getView(R.id.recipe_comment_field), comments);
        solo.waitForActivity("Timeout", pause);

        solo.clickOnButton("Add");

        solo.waitForActivity("Timeout", pause * 20);
    }



    @After
    public void tearDown(){
        solo.finishOpenedActivities();
    }
}
