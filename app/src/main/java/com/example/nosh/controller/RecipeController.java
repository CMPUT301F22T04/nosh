package com.example.nosh.controller;


import android.content.Context;

import com.example.nosh.database.controller.DBController;
import com.example.nosh.database.controller.FirebaseStorageController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.repository.RecipeImageRepository;
import com.example.nosh.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.Observer;


public class RecipeController {

    private final RecipeRepository recipeRepository;

    public RecipeController(Context context, DBController dbController,
                            FirebaseStorageController storageController, Observer o) {

        recipeRepository = new RecipeRepository(
                dbController,
                new RecipeImageRepository(context, storageController)
        );

        recipeRepository.addObserver(o);
        recipeRepository.sync();
    }

    /**
     * Receive all necessary information from users to create a a new recipe
     * @param preparationTime
     * @param servings
     * @param category
     * @param comments
     * @param photograph
     * @param title
     * @param ingredients
     */
    public void add(double preparationTime, int servings, String category, String comments,
                    String photograph, String title, ArrayList<Ingredient> ingredients) {

        recipeRepository.add(preparationTime, servings, category, comments,
                photograph, title, ingredients);
    }

    /**
     * Return a list of copy references of recipe objects
     * @return
     */
    public ArrayList<Recipe> retrieve() {
        return recipeRepository.retrieve();
    }

    /**
     * Delete a recipe
     * @param recipe
     */
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }
}
