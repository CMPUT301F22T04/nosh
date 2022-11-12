package com.example.nosh.utils;

import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.Recipe;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EntityUtil {

    public static Map<String, Object> mealPlanToMap(MealPlan mealPlan) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", mealPlan.getName());
        data.put("start", mealPlan.getStartDate());
        data.put("end", mealPlan.getEndDate());

        return data;
    }

    public static Map<String, Object> recipeToMap(Recipe recipe) {

        Map<String, Object> data = new HashMap<>();

        data.put("preparationTime", recipe.getPreparationTime());
        data.put("servings", recipe.getServings());
        data.put("category", recipe.getCategory());
        data.put("comments", recipe.getComments());
        data.put("photographRemote", recipe.getPhotographRemote());
        data.put("title", recipe.getName());
        data.put("hashcode", recipe.getHashcode());

        return data;
    }

    public static Map<String, Object> ingredientToMap(Ingredient ingredient) {

        Map<String, Object> data = new HashMap<>();

        data.put("inStorage", ingredient.isInStorage());
        data.put("bestBeforeDate", ingredient.getBestBeforeDate());
        data.put("unit", ingredient.getUnit());
        data.put("amount", ingredient.getAmount());
        data.put("category", ingredient.getCategory());
        data.put("description", ingredient.getDescription());
        data.put("location", ingredient.getLocation());
        data.put("name", ingredient.getName());
        data.put("hashcode", ingredient.getHashcode());

        return data;
    }

    public static Ingredient mapToIngredient(Map<String, Object> map) {
        Ingredient ingredient = new Ingredient();

        ingredient.setInStorage((Boolean)
                Objects.requireNonNull(map.get("inStorage")));

        if (map.containsKey("bestBeforeDate")) {
            ingredient.setBestBeforeDate(((Timestamp)
                    (Objects.requireNonNull(map.get("bestBeforeDate")))).toDate());
        }

       ingredient.setUnit((Double)
               Objects.requireNonNull(map.get("unit")));

        ingredient.setAmount((Long)
                Objects.requireNonNull(map.get("amount")));

        ingredient.setCategory((String) map.get("category"));
        ingredient.setDescription((String) map.get("description"));
        ingredient.setLocation((String) map.get("location"));
        ingredient.setName((String) map.get("name"));
        ingredient.setHashcode((String) map.get("hashcode"));

        return ingredient;
    }

    public static Recipe mapToRecipe(Map<String, Object> map) {
        Recipe recipe = new Recipe();

        recipe.setPreparationTime((Double)
                Objects.requireNonNull(map.get("preparationTime")));

        recipe.setServings((Long)
                Objects.requireNonNull(map.get("servings")));

        recipe.setCategory((String) map.get("category"));
        recipe.setComments((String) map.get("comments"));
        recipe.setPhotographRemote((String) map.get("photographRemote"));
        recipe.setTitle((String) map.get("title"));

        if (map.containsKey("ingredients")) {

            ArrayList<Ingredient> ingredients = new ArrayList<>();

            // TODO: Caution
            //noinspection unchecked
            for (Map<String, Object> pairs : (ArrayList<Map<String, Object>>)
                    Objects.requireNonNull(map.get("ingredients"))) {
                ingredients.add(mapToIngredient(pairs));
            }

            recipe.setIngredients(ingredients);
        }

        recipe.setHashcode((String) map.get("hashcode"));

        return recipe;
    }
}
