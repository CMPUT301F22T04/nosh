package com.example.nosh.repository;


import com.example.nosh.database.controller.DBController;
import com.example.nosh.database.controller.FirebaseStorageController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;



public class RecipeRepository extends Repository {

    private final HashMap<String, Recipe> recipes;
    private final RecipeImageRepository recipeImageRepository;

    public RecipeRepository(DBController dbController,
                            RecipeImageRepository recipeImageRepository) {
        super(dbController);

        this.recipeImageRepository = recipeImageRepository;

        recipes = new HashMap<>();
    }

    public void add(double preparationTime, int servings, String category, String comments,
                    String photograph, String title, ArrayList<Ingredient> ingredients) {

        Recipe recipe = new Recipe(preparationTime, servings, category, comments,
                photograph, title, ingredients);

        recipes.put(recipe.getHashcode(), recipe);

        super.add(recipe);
    }

    public void update(double preparationTime, int servings, String category, String comments,
                       String photograph, String title, ArrayList<Ingredient> ingredients) {

    }

    public void delete(Recipe recipe) {
        recipes.remove(recipe.getHashcode());

        super.delete(recipe);
    }

    public ArrayList<Recipe> retrieve() {
        return new ArrayList<>(recipes.values());
    }

    @Override
    public void update(Observable o, Object arg) {
        assert (arg instanceof Recipe[]);

        Recipe[] recipes = (Recipe[]) arg;

        for (Recipe recipe : recipes) {
            this.recipes.put(recipe.getHashcode(), recipe);
        }

        notifyObservers();
    }
}
