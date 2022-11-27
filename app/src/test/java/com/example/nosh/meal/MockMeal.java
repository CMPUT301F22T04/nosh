package com.example.nosh.meal;

import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;

public class MockMeal extends Meal {

    public MockMeal() {
        super();
    }

    public MockMeal(int servings, String name) {
        super(servings, name);
    }

    public MockMeal(MockMeal mockMeal) {
        super(mockMeal);
    }

    public boolean hasMealComponent(MealComponent mealComponent) {
        return mealComponents.containsKey(mealComponent.getHashcode());
    }

    public MealComponent getMealComponent(MealComponent mealComponent) {
        return mealComponents.get(mealComponent.getHashcode());
    }
}
