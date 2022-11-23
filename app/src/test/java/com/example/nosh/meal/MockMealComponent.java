package com.example.nosh.meal;

import com.example.nosh.entity.MealComponent;
import com.example.nosh.utils.DateUtil;

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
