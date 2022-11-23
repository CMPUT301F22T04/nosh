package com.example.nosh;

import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
import com.example.nosh.entity.MealPlanComponent;
import com.example.nosh.meal.MockMeal;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;


public class TestIterable {

    @Test
    public void testMealIterable() {
        MockIngredient ingredient = new MockIngredient();
        MockRecipe recipe = new MockRecipe();

        Meal meal = new Meal();

        HashMap<String, MealComponent> mealComponents = new HashMap<>();

        mealComponents.put(ingredient.getHashcode(), ingredient);
        mealComponents.put(recipe.getHashcode(), recipe);

        for (MealComponent mealComponent :
                meal) {
            assert mealComponents.containsKey(mealComponent.getHashcode());
        }
    }

    @Test
    public void testMealPlanIterable() {
        MockMeal mealA = new MockMeal();
        MockMeal mealB = new MockMeal();

        MealPlanComponent mealPlanComponent = new MealPlanComponent();

        mealPlanComponent.addMeal(mealA);
        mealPlanComponent.addMeal(mealB);

        HashSet<String> hashSet = new HashSet<>();
        hashSet.add(mealA.getHashcode());
        hashSet.add(mealB.getHashcode());

        for (Meal meal :
                mealPlanComponent) {
            assert hashSet.contains(meal.getHashcode());
        }
    }
}
