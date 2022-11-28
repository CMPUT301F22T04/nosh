package com.example.nosh.repository;

import com.example.nosh.database.controller.RecipeDBController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class is responsible for the Recipe repository.
 */


@Singleton
public class RecipeRepository extends Repository {

    private final HashMap<String, Recipe> recipes;

    @Inject
    public RecipeRepository(RecipeDBController dbController) {
        super(dbController);

        recipes = new HashMap<>();

        sync();
    }

    public void add(double preparationTime, long servings, String category, String comments,
                    String photographRemote, String title,
                    ArrayList<Ingredient> ingredients) {

        Recipe recipe = new Recipe(preparationTime, servings, category, comments,
                photographRemote, title, ingredients);

        recipes.put(recipe.getHashcode(), recipe);

        super.add(recipe);
    }

    public void update(String hashcode, double preparationTime, long servings,
                       String category, String comments, String photographRemote,
                       String title, ArrayList<Ingredient> ingredients) {

        Recipe recipe = Objects.requireNonNull(recipes.get(hashcode));

        recipe.setPreparationTime(preparationTime);
        recipe.setServings(servings);
        recipe.setCategory(category);
        recipe.setComments(comments);
        recipe.setPhotographRemote(photographRemote);
        recipe.setTitle(title);

        // TODO : a better way update ingredients ?
        recipe.setIngredients(ingredients);

        recipes.put(hashcode, recipe);

        super.update(recipe);
    }

    public void updateIngredientInRecipe(Ingredient ingredient) {
        String hashcode = ingredient.getHashcode();
        for (Recipe recipe : recipes.values()) {
            if (recipe.hasIngredient(hashcode)) {
                recipe.updateIngredient(ingredient);

                super.update(recipe);

                break;
            }
        }
    }

    public void delete(Recipe recipe) {
        recipes.remove(recipe.getHashcode());

        super.delete(recipe);
    }

    public ArrayList<Recipe> retrieve() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        for (Recipe recipe :
                this.recipes.values()) {
            recipes.add(new Recipe(recipe));
        }

        return recipes;
    }

    Recipe retrieve(String hashcode) {
        return recipes.get(hashcode);
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
