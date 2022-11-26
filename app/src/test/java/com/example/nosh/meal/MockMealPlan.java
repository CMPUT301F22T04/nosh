package com.example.nosh.meal;

import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.MealPlanComponent;

import java.util.Date;

/**
 * This class is a Mock Meal Plan class for unit tests.
 * @version 1.0
 */
public class MockMealPlan extends MealPlan {

    MockMealPlan(String name, Date startDate, Date endDate) {
        super(name, startDate, endDate);
    }

    MealPlanComponent getMealFromDay(String date) {
        return plans.get(date);
    }
}
