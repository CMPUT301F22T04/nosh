package com.example.nosh.meal;

import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.MealComponent;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Objects;


public class TestMealComponent {

    @Test
    public void testClone() {
        Ingredient ingredient;

        ingredient = new Ingredient(new Date(), 1.0, 1, "ABC", "ABC", "ABC", "ABC");

        MealComponent component = (MealComponent) ingredient.clone();

        Ingredient ingr = (Ingredient) component;

        ingr.setName("DEF");

        assert !Objects.equals(ingr.getName(), ingredient.getName());
    }
}
