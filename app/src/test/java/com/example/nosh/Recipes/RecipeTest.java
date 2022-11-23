package com.example.nosh.Recipes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RecipeTest {

    private Recipe mockRecipe() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        return new Recipe(3, 4, "Rice","A super easy, full flavoured Butter Chicken recipe",
                "newImage", "Butter Chicken", ingredients);
    }

    @Test
    void testGetTitle() {
        Recipe recipe = mockRecipe();
        assertEquals("Butter Chicken", recipe.getTitle());
    }

    @Test
    void testSetTitle() {
        Recipe recipe = mockRecipe();
        recipe.setTitle("Tasty Butter Chicken");
        assertEquals("Tasty Butter Chicken", recipe.getTitle());
    }

    @Test
    void testGetCategory() {
        Recipe recipe = mockRecipe();
        assertEquals("Rice", recipe.getCategory());
    }

    @Test
    void testSetCategory() {
        Recipe recipe = mockRecipe();
        recipe.setCategory("Indian dish");
        assertEquals("Indian dish", recipe.getCategory());
    }

    @Test
    void testGetRecipeComments() {
        Recipe recipe = mockRecipe();
        assertEquals("A super easy, full flavoured Butter Chicken recipe", recipe.getComments());
    }

    @Test
    void testSetRecipeComments() {
        Recipe recipe = mockRecipe();
        recipe.setComments("Can be done in 3 hours");
        assertEquals("Can be done in 3 hours", recipe.getComments());
    }

    @Test
    void testGetServings() {
        Recipe recipe = mockRecipe();
        long number = 4;
        assertEquals(number, recipe.getServings());
    }

    @Test
    void testSetServings() {
        Recipe recipe = mockRecipe();
        long number = 3;
        recipe.setServings(number);
        assertEquals(number, recipe.getServings());
    }

    @Test
    void testGetPreparationTime() {
        Recipe recipe = mockRecipe();
        double time = 3;
        assertEquals(time, recipe.getPreparationTime());
    }

    @Test
    void testSetPreparationTime() {
        Recipe recipe = mockRecipe();
        double time = 1;
        recipe.setPreparationTime(time);
        assertEquals(time, recipe.getPreparationTime());
    }

    @Test
    void testGetRecipeIngredientsList() {
        Recipe recipe = mockRecipe();
        assertEquals(new ArrayList<Ingredient>(), recipe.getIngredients());
    }
}