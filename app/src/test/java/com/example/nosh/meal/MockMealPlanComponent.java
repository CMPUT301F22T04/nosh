package com.example.nosh.meal;

import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealPlanComponent;

/**
 * This class is a Mock Meal Plan Component class for unit tests.
 * @version 1.0
 */
class MockMealPlanComponent extends MealPlanComponent {

    public MockMealPlanComponent() {
        super();
    }

    public MockMealPlanComponent(MockMealPlanComponent mealPlanComponent) {
        super(mealPlanComponent);
    }

    boolean hasMeal(Meal meal) {
        return meals.containsKey(meal.getHashcode());
    }

    Meal getMeal(String hashcode) {
        return meals.get(hashcode);
    }
}
