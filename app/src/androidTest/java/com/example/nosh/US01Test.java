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
public class US01Test {
    // Testing variables declaration
    private Solo solo;
    private final int pause = 100;

    // Sample Ingredient (Spaghetti) Information
    String name1 = "Spaghetti";
    String description1 = "A long, thin pasta.";
    String location1 = "Cabinet";
    int year1 = 2025, month1 = 11, day1 = 20;
    String date1 = year1 + "-" + month1 + "-" + day1;
    int quantity1 = 500;
    int unit1 = 1;
    String category1 = "Pasta";

    // Sample Ingredient (Salt) Information
    String name2 = "Salt";
    String description2 = "A universal flavour improver.";
    String location2 = "Counter-top";
    int year2 = 2030, month2 = 10, day2 = 12;
    String date2 = year2 + "-" + month2 + "-" + day2;
    int quantity2 = 300;
    int unit2 = 4;
    String category2 = "Flavouring";

    // Sample Ingredient (Tomato Sauce) Information
    String name3 = "Tomato Sauce";
    String description3 = "A simple tomato sauce.";
    String location3 = "Cabinet";
    int year3 = 2023, month3 = 11, day3 = 25;
    String date3 = year3 + "-" + month3 + "-" + day3;
    int quantity3 = 398;
    int unit3 = 2;
    String category3 = "Sauce";

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
    }

    /**
     * US 01.01.01 Test
     * This test if the app adds an ingredient successfully.
     */
    @Test
    public void US010101Test(){
        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause);

        // Adds ingredient
        addIngredient(name1, description1, location1, year1, month1, day1, quantity1,
                unit1, category1);
    }

    /**
     * US 01.02.01 Test
     * This test if the app adds an ingredient correctly in the first test.
     */
    @Test
    public void US010201Test(){
        // Clicks on the ingredient
        solo.clickOnText(name1);

        // Check if info match
        Assert.assertTrue("Search name", solo.searchText(name1));
        Assert.assertTrue("Search description", solo.searchText(description1));
        Assert.assertTrue("Search location", solo.searchText(location1));
        Assert.assertTrue("Search date", solo.searchText(date1));
        Assert.assertTrue("Search quantity", solo.searchText(String.valueOf(quantity1)));
        Assert.assertTrue("Search quantity",
                solo.searchText(String.valueOf(Double.valueOf(unit1))));
        Assert.assertTrue("Search category", solo.searchText(category1));
        solo.waitForActivity("Timeout", pause * 5);

        // Go back to list
        solo.goBack();
        solo.waitForActivity("Timeout", pause);
    }

    /**
     * US 01.03.01 Test
     * This test edit the stored ingredient in the first test and checks if new info is saved
     * correctly.
     */
    @Test
    public void US010301Test(){
        // Info to be edited
        String locationEdit = "Jar";
        int quantityEdit = 400;

        // Click to view and edit info
        solo.clickOnText(name1);
        solo.clearEditText((EditText) solo.getView(R.id.edit_storage_location));
        solo.enterText((EditText) solo.getView(R.id.edit_storage_location), locationEdit);
        solo.waitForActivity("Timeout", pause);
        solo.clearEditText((EditText) solo.getView(R.id.edit_qty));
        solo.enterText((EditText) solo.getView(R.id.edit_qty), String.valueOf(quantityEdit));
        solo.waitForActivity("Timeout", pause);
        solo.clickOnButton("Confirm");
        solo.waitForActivity("Timeout", pause * 3);

        // Check if new info is stored correctly
        solo.clickOnText(name1);
        Assert.assertTrue("Search name", solo.searchText(name1));
        Assert.assertTrue("Search description", solo.searchText(description1));
        Assert.assertTrue("Search location", solo.searchText(locationEdit));
        Assert.assertTrue("Search date", solo.searchText(date1));
        Assert.assertTrue("Search quantity", solo.searchText(String.valueOf(quantityEdit)));
        Assert.assertTrue("Search quantity",
                solo.searchText(String.valueOf(Double.valueOf(unit1))));
        Assert.assertTrue("Search category", solo.searchText(category1));
        solo.waitForActivity("Timeout", pause * 5);

        // Go back to list
        solo.goBack();
        solo.waitForActivity("Timeout", pause);
    }

    /**
     * US 01.04.01 Test
     * This test deletes the ingredient and checks if it is properly deleted.
     */
    @Test
    public void US010401Test(){
        solo.clickOnView(solo.getView("del_btn"));

        // Checks if the item is no longer in the list
        Assert.assertFalse("Search for \"Spaghetti\"", solo.searchText(name1));
        solo.waitForActivity("Timeout", pause * 5);
    }

    /**
     * US 01.05.01 Test
     * This test will first add three ingredients to the list and then checks of they are
     * displayed correctly.
     */
    @Test
    public void US010501Test(){
        // Add the three ingredients to the list
        addIngredient(name1, description1, location1, year1, month1, day1, quantity1,
                unit1, category1);
        addIngredient(name2, description2, location2, year2, month2, day2, quantity2,
                unit2, category2);
        addIngredient(name3, description3, location3, year3, month3, day3, quantity3,
                unit3, category3);

        // Checks if the ingredients are in the list
        Assert.assertTrue("Search for Ingredient 1", solo.searchText(name1));
        Assert.assertTrue("Search for Ingredient 2", solo.searchText(name2));
        Assert.assertTrue("Search for Ingredient 3", solo.searchText(name3));
        solo.waitForActivity("Timeout", pause * 10);
    }

    /**
     * US 01.06.01 Test
     * This test if sorts functions works.
     */
    @Test
    public void US010601Test(){
        // Various sort cases
        sortIngredients(0, 0);
        sortIngredients(1, 1);
        sortIngredients(0, 2);
        sortIngredients(1, 3);

        // Delete all items
        solo.waitForActivity("Timeout", pause * 20);
    }

    /**
     * This method adds an ingredient to the list given the info.
     * @param name Name of the ingredient
     * @param description Description of the ingredient
     * @param location Location of the ingredient
     * @param year Year of the best before date of the ingredient
     * @param month Month of the best before date of the ingredient
     * @param day Day of the best before date of the ingredient
     * @param quantity Quantity of the ingredient
     * @param unit Unit of the ingredient
     * @param category Category of the ingredient
     */
    public void addIngredient(String name, String description, String location, int year,
                              int month, int day, int quantity, int unit,
                              String category){
        // Press add button
        solo.clickOnView(solo.getView("add_btn"));
        solo.waitForActivity("Timeout", pause);

        // Enter ingredient info
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
        solo.waitForActivity("Timeout", pause * 2);
    }

    /**
     * This method sorts the ingredient list
     * @param order 0 is ascending, 1 is descending
     * @param criteria 0 is Description, 1 is Best Before date, 2 is Location, 3 is Category
     */
    public void sortIngredients(int order, int criteria){
        // Press sort button
        solo.clickOnView(solo.getView("sort_button"));
        solo.waitForActivity("Timeout", pause);

        // Pick order
        if (order == 0) {
            solo.clickOnText("Ascending");
        } else {
            solo.clickOnText("Descending");
        }

        // Pick criteria
        switch(criteria) {
            case 0:
                solo.clickOnText("Description");
                break;
            case 1:
                solo.clickOnText("Best Before Date");
                break;
            case 2:
                solo.clickOnText("Location");
                break;
            case 3:
                solo.clickOnText("Category");
                break;
        }

        // Press sort button
        solo.clickOnView(solo.getView("confirm_sort"));
        solo.waitForActivity("Timeout", pause * 10);
    }

    /**
     * This function clears the list.
     * @param count Total number of ingredients in the list
     */
    public void clearList(int count) {
        for (int i = 0; i < count; i++) {
            solo.clickOnView(solo.getView("del_btn"));
            solo.waitForActivity("Timeout", pause * 3);
        }
    }

    @After
    public void tearDown(){ solo.finishOpenedActivities();
    }
}
