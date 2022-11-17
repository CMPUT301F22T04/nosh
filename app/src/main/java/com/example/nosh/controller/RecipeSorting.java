package com.example.nosh.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.nosh.entity.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class RecipeSorting {

    public static void sort(ArrayList<Recipe> recipes, String sortBy,
                            boolean des) {

        Comparator<Recipe> comparator;

        switch (sortBy) {
            case "by_servings":
                comparator = Comparator.comparing(Recipe::getServings);
                break;
            case "by_prep":
                comparator = Comparator.comparing(Recipe::getPreparationTime);
                break;
            case "by_categoryR":
                comparator = Comparator.comparing(Recipe::getCategory);
                break;
            default: // Default sort by description
                comparator = Comparator.comparing(Recipe::getTitle);
                break;
        }

        comparator = des ? comparator : Collections.reverseOrder(comparator);

        recipes.sort(comparator);
    }
}
