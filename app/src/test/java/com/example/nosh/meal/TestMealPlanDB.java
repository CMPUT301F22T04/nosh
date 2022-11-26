package com.example.nosh.meal;

import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.Recipe;
import com.example.nosh.utils.DateUtil;

/**
 * This class is a responsible for generating meal plan test cases for unit tests.
 * @version 1.0
 */

public class TestMealPlanDB {

    public static MealPlan generateTestCase() {
        MealPlan mealPlan = new MealPlan(
                "Meal Plan 1",
                DateUtil.getCalendar("2022-11-17").getTime(),
                DateUtil.getCalendar("2022-11-27").getTime()
        );

        mealPlan.setHashcode("1");

        Meal mealA = new Meal(5, "2022-11-26", "hash","breakfast");
        mealA.setHashcode("2");

        Meal mealB = new Meal(10, "2022-11-26", "hash","lunch");
        mealB.setHashcode("3");

        MealComponent ingredientA = new Ingredient();
        ingredientA.setHashcode("4");
        MealComponent recipeA = new Recipe();
        recipeA.setHashcode("6");

        MealComponent ingredientB = new Ingredient();
        ingredientB.setHashcode("5");
        MealComponent recipeB = new Recipe();
        recipeB.setHashcode("7");

        mealA.addMealComponent(ingredientA);
        mealA.addMealComponent(recipeA);

        mealB.addMealComponent(ingredientB);
        mealB.addMealComponent(recipeB);

        mealPlan.addMealToDay("2022-11-20", mealA);
        mealPlan.addMealToDay("2022-11-25", mealB);

        return mealPlan;
    }
}
