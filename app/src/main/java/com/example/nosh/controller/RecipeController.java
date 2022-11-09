package com.example.nosh.controller;


import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.repository.RecipeImageRepository;
import com.example.nosh.repository.RecipeRepository;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * This class act a messenger between the view model (RecipeFragment) and
 * RecipeRepository and RecipeImageRepository.
 * @author Dekr0
 */
@Singleton
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeImageRepository recipeImageRepository;

    @Inject
    public RecipeController(RecipeRepository recipeRepository,
                            RecipeImageRepository recipeImageRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeImageRepository = recipeImageRepository;
    }

    /**
     * Pass all necessary information from users to create a a new recipe
     */
    public void add(double preparationTime, int servings, String category, String comments,
                    String photographLocal, String title, ArrayList<Ingredient> ingredients) {

        String photographRemote = recipeImageRepository.add(photographLocal);

        recipeRepository.add(preparationTime, servings, category, comments,
                photographRemote, title, ingredients);
    }

    /**
     * Return a list of copy references of recipe objects
     */
    public ArrayList<Recipe> retrieve() {
        return recipeRepository.retrieve();
    }

    /**
     * Return a list of StorageReference of recipe images in Firebase Storage
     */
    public HashMap<String, StorageReference> getRecipeImagesRemote() {
        return recipeImageRepository.getRecipeImagesRemote();
    }

    /**
     * Delete a recipe
     */
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    public void addObserver(Observer o) {
        recipeRepository.addObserver(o);
        recipeImageRepository.addObserver(o);
    }

    public void deleteObserver(Observer o) {
        recipeRepository.deleteObserver(o);
        recipeImageRepository.deleteObserver(o);
    }
}
