package com.example.nosh.controller;


import com.example.nosh.database.controller.DBController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.Observer;


public class RecipeController {

    private final RecipeRepository recipeRepository;

    public RecipeController(DBController dbController, Observer o) {
        recipeRepository = new RecipeRepository(dbController);

        recipeRepository.addObserver(o);
        recipeRepository.sync();
    }

    public void add(double preparationTime, int servings, String category, String comments,
                    String photograph, String title, ArrayList<Ingredient> ingredients) {

        recipeRepository.add(preparationTime, servings, category, comments,
                photograph, title, ingredients);
    }

    public ArrayList<Recipe> retrieve() {
        return recipeRepository.retrieve();
    }

    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }
}
