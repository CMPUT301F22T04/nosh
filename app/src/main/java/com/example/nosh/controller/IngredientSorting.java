package com.example.nosh.controller;

import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class IngredientSorting {

    public static void sort(ArrayList<Ingredient> ingredients, String sortBy,
                            boolean des) {

        Comparator<Ingredient> comparator;

        switch (sortBy) {
            case "by_date":
                comparator = Comparator.comparing(Ingredient::getBestBeforeDate);
                break;
            case "by_location":
                comparator = Comparator.comparing(Ingredient::getLocation);
                break;
            case "by_category":
                comparator = Comparator.comparing(Ingredient::getCategory);
                break;
            default: // Default sort by description
                comparator = Comparator.comparing(Ingredient::getDescription);
                break;
        }

        comparator = des ? comparator : Collections.reverseOrder(comparator);

        ingredients.sort(comparator);
    }
}
