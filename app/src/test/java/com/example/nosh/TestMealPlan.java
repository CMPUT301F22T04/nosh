package com.example.nosh;

import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.utils.DateUtil;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class TestMealPlan {

    ArrayList<Meal> meals;
    ArrayList<MealComponent> ingredients;
    ArrayList<MealComponent> recipes;
    MealPlan mealPlan;

    public void setup() {
        meals = new ArrayList<>();
        ingredients = new ArrayList<>();
        recipes = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ingredients.add(new MockIngredient());
            recipes.add(new MockRecipe());
        }

        Meal mealA = new Meal(5, "breakfast");
        Meal mealB = new Meal(10, "lunch");

        mealA.setMealComponents(ingredients);
        mealB.setMealComponents(recipes);

        meals.add(mealA);
        meals.add(mealB);
    }

    @Test
    public void testDate() {
        setup();

        mealPlan = new MealPlan(
                "Meal Plan 1",
                DateUtil.getCalendar("2022-11-17").getTime(),
                DateUtil.getCalendar("2022-11-27").getTime()
        );

        mealPlan.addMealToDay("2022-11-18", meals.get(0));
        mealPlan.addMealToDay("2022-11-27", meals.get(1));
    }
}
