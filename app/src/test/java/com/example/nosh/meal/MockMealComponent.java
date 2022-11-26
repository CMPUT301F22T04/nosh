package com.example.nosh.meal;

import com.example.nosh.entity.MealComponent;
import com.example.nosh.utils.DateUtil;

/**
 * This class is a Mock Meal Component class for unit tests.
 * @version 1.0
 */
class MockMealComponent extends MealComponent {

    private String name;

    MockMealComponent() {
        super();
    }

    MockMealComponent(String name) {
        super();

        this.name = name;
    }

    MockMealComponent(MockMealComponent mockMealComponent) {
        name = mockMealComponent.getName();
        hashcode = mockMealComponent.getHashcode();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHashcode() {
        return hashcode;
    }
}
