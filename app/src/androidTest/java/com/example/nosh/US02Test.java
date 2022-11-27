package com.example.nosh;

import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

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

/**
 * This class is responsible for intent tests of all US 02.
 * @author Lok Him Isaac Cheng
 * @version 1.6
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class US02Test {
    // Testing variables declaration
    private Solo solo;
    private final int pause = 100;

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

    // Sample Ingredient 1 (Spaghetti)
    MockIngredient ing1 = new MockIngredient("Spaghetti",
            "A long, thin pasta.", 500, "gram", "Pasta");

    // Sample Ingredient 2 (Tomato)
    MockIngredient ing2 = new MockIngredient("Tomato",
            "A healthy nutritious fruit.", 300, "gram", "Fruit");

    // Sa,ple Ingredient 3 (Cheese)
    MockIngredient ing3 = new MockIngredient("Cheese",
            "Coagulation of the milk protein.", 50, "gram", "Dairy");

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
     * cannot be tested, it has to be added manually during the test
     */
    //@Test
    public void US020101Test(){
        ArrayList<MockIngredient> ingredients = new ArrayList<>();
        ingredients.add(ing1);
        ingredients.add(ing2);
        addRecipe(title1, time1, servings1, category1, comments1, ingredients);
        solo.waitForActivity("Timeout", pause * 20);
    }

    /**
     * US 02.02.01 Test
     * This test if the app adds an ingredient to a recipe successfully.
     */
    //@Test
    public void US020201Test(){
        solo.clickOnText("Tomato Sauce Spaghetti");
        solo.waitForActivity("Timeout", pause * 40);
        solo.clickOnView(solo.getView(R.id.add_recipe_back_btn));
        solo.waitForActivity("Timeout", pause * 20);
    }

    /**
     * US 02.03.01 Test
     * This test if the app deletes an ingredient from a recipe successfully.
     */
    //@Test
    public void US020301Test(){
        // delete ingredient button: del_btn_recipe_ingredient
        // edit ingredient button: edit_btn_recipe_ingredient
        solo.clickOnText("Tomato Sauce Spaghetti");
        solo.waitForActivity("Timeout", pause * 20);
        solo.clickOnView(solo.getView(R.id.del_btn_recipe_ingredient));
        solo.waitForActivity("Timeout", pause * 20);
    }

    /**
     * US 02.04.01 Test
     * This test if the app view details properly.
     */
    //@Test
    public void US020401Test(){
        solo.clickOnText("Tomato Sauce Spaghetti");
        solo.waitForActivity("Timeout", pause * 40);
        solo.clickOnView(solo.getView(R.id.add_recipe_back_btn));
        solo.waitForActivity("Timeout", pause * 20);
    }

    /**
     * US 02.05.01 Test
     * This test if the app edit details properly.
     */
    //@Test
    public void US020501Test(){
        solo.clickOnText("Tomato Sauce Spaghetti");

        String newName = "Tomato Cheese Spaghetti";
        solo.clearEditText((EditText) solo.getView(R.id.recipe_name_field));
        solo.enterText((EditText) solo.getView(R.id.recipe_name_field), newName);

        int time = 30;
        solo.clearEditText((EditText) solo.getView(R.id.preparation_time_field));
        solo.enterText((EditText) solo.getView(R.id.preparation_time_field), String.valueOf(time));

        ArrayList<MockIngredient> ingredients= new ArrayList<>();
        addIngredientsToRecipe(ing1);
        addIngredientsToRecipe(ing3);

        String description = "A tasty pasta for everyone with cheese.";
        solo.clearEditText((EditText) solo.getView(R.id.recipe_comment_field));
        solo.enterText((EditText) solo.getView(R.id.recipe_comment_field), description);

        solo.clickOnView(solo.getView(R.id.submit_recipe));
        solo.waitForActivity("Timeout", pause * 20);

        solo.clickOnText(newName);
        solo.waitForActivity("Timeout", pause * 20);

    }

    /**
     * US 02.06.01 Test
     * This test if the app deletes a recipe successfully.
     */
    //@Test
    public void US020601Test(){
        // delete button del_btnR
        solo.clickOnView(solo.getView(R.id.del_btnR));
        solo.waitForActivity("Timeout", pause * 40);
    }

    /**
     * US 02.07.01 Test
     * This test checks if a list of recipe is displayed correctly.
     */
    //@Test
    public void US020701Test(){
        ArrayList<MockIngredient> ingredients = new ArrayList<>();
        addRecipe(title1, time1, servings1, category1, comments1, ingredients);
        addRecipe(title2, time2, servings2, category2, comments2, ingredients);
        addRecipe(title3, time3, servings3, category3, comments3, ingredients);
        solo.waitForActivity("Timeout", pause * 100);
    }

    /**
     * US 02.08.01 Test
     * This test if recipe sorted correctly.
     */
    @Test
    public void US020801Test(){
        sortRecipe(0, 0);
        sortRecipe(1, 1);
        sortRecipe(0, 2);
        sortRecipe(1, 3);
    }

    public void addRecipe(String title, int time, int servings, String category, String comments,
                          ArrayList<MockIngredient> ingredients){
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
        solo.waitForActivity("Timeout", 8000);
        for (int i = 0; i < ingredients.size(); i++) {
            addIngredientsToRecipe(ingredients.get(i));
        }
        solo.enterText((EditText) solo.getView(R.id.recipe_comment_field), comments);
        solo.waitForActivity("Timeout", pause);
        solo.clickOnView(solo.getView(R.id.submit_recipe));
        solo.waitForActivity("Timeout", pause * 20);
    }

    public void addIngredientsToRecipe(MockIngredient ingredient){
        // Extract fields
        String name = ingredient.ingName;
        String description = ingredient.ingDescription;
        int quantity = ingredient.quantity;
        String unit = ingredient.unit;
        String category = ingredient.ingCategory;

        // Press add button
        solo.clickOnView(solo.getView(R.id.add_recipe_ingredient));

        // Enter fields
        solo.enterText((EditText) solo.getView(R.id.add_name_recipe_ingredient), name);
        solo.enterText((EditText) solo.getView(R.id.add_description_recipe_ingredient),
                description);
        solo.enterText((EditText) solo.getView(R.id.add_qty_recipe_ingredient),
                String.valueOf(quantity));
        solo.enterText((EditText) solo.getView(R.id.add_unit_recipe_ingredient), unit);
        solo.enterText((EditText) solo.getView(R.id.add_ingredient_recipe_category), category);
        solo.clickOnView(solo.getView(R.id.submit_add_recipe_ingredient));
        solo.waitForActivity("Timeout", pause * 10);
    }

    public void sortRecipe(int order, int criteria){
        // Press sort button
        solo.clickOnView(solo.getView("sort_buttonR"));
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
        solo.clickOnView(solo.getView("confirm_sortR"));
        solo.waitForActivity("Timeout", pause * 10);
    }


    @After
    public void tearDown(){
        solo.finishOpenedActivities();
    }
}
