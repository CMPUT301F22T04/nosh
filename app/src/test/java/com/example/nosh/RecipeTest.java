package com.example.nosh;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class RecipeTest {

    private Recipe mockRecipe() {
        return new Recipe(3, 4, "Rice","A super easy, full flavoured Butter Chicken recipe",
                "newImage", "Butter Chicken", null);
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
        assertEquals("Can be done 3 hours", recipe.getComments());
    }

    @Test
    void testGetServings() {
        Recipe recipe = mockRecipe();
        Integer number = 4;
        assertEquals(Optional.of(number), recipe.getServings());
    }

    @Test
    void testSetServings() {
        Recipe recipe = mockRecipe();
        Integer number = 3;
        recipe.setServings(number);
        assertEquals(number, Optional.of(recipe.getServings()));
    }

    @Test
    void testGetPreparationTime() {
        Recipe recipe = mockRecipe();
        Integer time = 3;
        assertEquals(Optional.of(time), recipe.getPreparationTime());
    }

    @Test
    void testSetPreparationTime() {
        Recipe recipe = mockRecipe();
        Integer time = 1;
        recipe.setPreparationTime(time);
        assertEquals(Optional.of(time), recipe.getPreparationTime());
    }

    @Test
    void testGetRecipeIngredientsList() {
        Recipe recipe = mockRecipe();
        assertEquals(null, recipe.getIngredients());
    }

    @Test
    void testSetRecipeIngredientsList() {
        //TODO:
    }



}

