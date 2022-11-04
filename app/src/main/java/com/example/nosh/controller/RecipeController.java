package com.example.nosh.controller;


import android.content.Context;
import android.net.Uri;

import com.example.nosh.database.controller.DBController;
import com.example.nosh.database.controller.FirebaseStorageController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.repository.RecipeImageRepository;
import com.example.nosh.repository.RecipeRepository;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;


public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeImageRepository recipeImageRepository;

    public RecipeController(Context context, DBController dbController,
                            FirebaseStorageController storageController, Observer o) {

        recipeRepository = new RecipeRepository(dbController);
        recipeImageRepository = new RecipeImageRepository(context, storageController);

        recipeRepository.addObserver(o);
        recipeImageRepository.addObserver(o);

        recipeRepository.sync();
        recipeImageRepository.sync();
    }

    /**
     * Receive all necessary information from users to create a a new recipe
     * @param preparationTime
     * @param servings
     * @param category
     * @param comments
     * @param photographLocal
     * @param title
     * @param ingredients
     */
    public void add(double preparationTime, int servings, String category, String comments,
                    String photographLocal, String title, ArrayList<Ingredient> ingredients) {

        String photographRemote = recipeImageRepository.add(photographLocal);

        recipeRepository.add(preparationTime, servings, category, comments,
                photographLocal, photographRemote, title, ingredients);
    }

    /**
     * Return a list of copy references of recipe objects
     * @return
     */
    public ArrayList<Recipe> retrieve() {
        return recipeRepository.retrieve();
    }

    public HashMap<String, StorageReference> getRecipeImagesRemote() {
        return recipeImageRepository.getRecipeImagesRemote();
    }

    /**
     * Delete a recipe
     * @param recipe
     */
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }
}
