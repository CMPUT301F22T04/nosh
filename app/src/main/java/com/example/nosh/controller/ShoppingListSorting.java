package com.example.nosh.controller;

import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class contains a static method for sorting the shopping list by
 * specific criteria
 * @author Julian Gallego Franco
 * @version 1.0
 */
public class ShoppingListSorting {

    /**
     * Sort a list of ingredients by specific criteria
     * @param sortBy
     */
    public static void sort(String sortBy) {
        Comparator<Ingredient> comparator;

        switch (sortBy) {
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

        comparator = Collections.reverseOrder(comparator);

    }
}
