package com.example.nosh.controller;


import android.net.Uri;

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
    public void add(double preparationTime, long servings, String category, String comments,
                    Uri localPhotoUri, String title, ArrayList<Ingredient> ingredients) {

        String photographRemote = recipeImageRepository.add(localPhotoUri);

        recipeRepository.add(preparationTime, servings, category, comments,
                photographRemote, title, ingredients);
    }

    /**
     * Return a list of copy references of recipe objects
     */
    public ArrayList<Recipe> retrieve() {
        return recipeRepository.retrieve();
    }

    public void scaleSevering(String hashcode, long servings) {
        recipeRepository.scaleServings(hashcode, servings);
    }

    public void update(String hashcode, double preparationTime, long servings,
                       String category, String comments, Uri localPhotoUri,
                       String title, ArrayList<Ingredient> ingredients) {
        String photographRemote = recipeImageRepository.add(localPhotoUri);

        recipeRepository.update(hashcode, preparationTime, servings, category,
                comments, photographRemote, title, ingredients);
    }

    public void update(String hashcode, double preparationTime, long servings,
                       String category, String comments, String photographRemote,
                       String title, ArrayList<Ingredient> ingredients) {
        recipeRepository.update(hashcode, preparationTime, servings, category,
                comments, photographRemote, title, ingredients);
    }

    /**
     * Return a list of StorageReference of recipe images in Firebase Storage
     */
    public HashMap<String, StorageReference> getRecipeImagesRemote() {
        return recipeImageRepository.getRecipeImagesRemote();
    }

    public StorageReference getRecipeImageRemote(String remoteLocation) {
        return recipeImageRepository.getRecipeImageRemote(remoteLocation);
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
