package com.example.nosh.utils;

import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.Recipe;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is responsible for the entity utility.
 */

public class EntityUtil {

    public static Map<String, Object> mealToMap(Meal meal) {
        Map<String, Object> data = new HashMap<>();

        data.put("servings", meal.getServings());
        data.put("name", meal.getName());
        data.put("usedDate", meal.getUsedDate());
        data.put("usedPlanHash", meal.getUsedPlanHash());
        data.put("hashcode", meal.getHashcode());

        ArrayList<String> mealComponentsHashcode = new ArrayList<>();

        for (MealComponent mealComponent :
             meal) {
            mealComponentsHashcode.add(mealComponent.getHashcode());
        }

        data.put("mealComponents", mealComponentsHashcode);

        return data;
    }

    public static Map<String, Object> mealPlanToMap(MealPlan mealPlan) {
        Map<String, Object> data = new HashMap<>();
        data.put("startDate", mealPlan.getStartDate());
        data.put("endDate", mealPlan.getEndDate());
        data.put("totalDays", mealPlan.getTotalDays());
        data.put("name", mealPlan.getName());
        data.put("hashcode", mealPlan.getHashcode());

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

        try {
            ingredient.setUnit((String)
                    Objects.requireNonNull(map.get("unit")));
        } catch (ClassCastException e) {
            ingredient.setUnit((String)
                    Objects.requireNonNull(map.get("unit")));
        }

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

        try {
                recipe.setPreparationTime((Double)
                        Objects.requireNonNull(map.get("preparationTime")));
        } catch (ClassCastException e) {
                recipe.setPreparationTime((double) (Long)
                        Objects.requireNonNull(map.get("preparationTime")));
        }

        recipe.setServings((Long)
                Objects.requireNonNull(map.get("servings")));

        recipe.setCategory((String) map.get("category"));
        recipe.setComments((String) map.get("comments"));
        recipe.setPhotographRemote((String) map.get("photographRemote"));
        recipe.setTitle((String) map.get("title"));

        if (map.containsKey("ingredients")) {

            ArrayList<Ingredient> ingredients = new ArrayList<>();

            // TODO: Caution
            for (Map<String, Object> pairs : (ArrayList<Map<String, Object>>)
                    Objects.requireNonNull(map.get("ingredients"))) {
                ingredients.add(mapToIngredient(pairs));
            }

            recipe.setIngredients(ingredients);
        }

        recipe.setHashcode((String) map.get("hashcode"));

        return recipe;
    }

    public static MealPlan mapToMealPlan(Map<String, Object> map) {
        Date startDate = ((Timestamp) Objects.requireNonNull(map.get("startDate"))).toDate();
        Date endDate = ((Timestamp) Objects.requireNonNull(map.get("endDate"))).toDate();
        String name = (String) map.get("name");
        String hashcode = (String) map.get("hashcode");

        MealPlan mealPlan = new MealPlan(name, startDate, endDate);

        mealPlan.setHashcode(hashcode);

        return mealPlan;
    }

    public static Meal mapToMeal(Map<String, Object> map) {
        Meal meal = new Meal();

        if (map.containsKey("mealComponents")) {
            for (String hashcode : (ArrayList<String>)
                    Objects.requireNonNull(map.get("mealComponents"))) {

                // Placeholder
                Ingredient ingredient = new Ingredient();
                ingredient.setHashcode(hashcode);

                meal.addMealComponent(ingredient);
            }
        }

        meal.setServings((Long) Objects.requireNonNull(map.get("servings")));
        meal.setName((String) map.get("name"));
        meal.setUsedDate((String) map.get("usedDate"));
        meal.setUsedPlanHash((String) map.get("usedPlanHash"));
        meal.setHashcode((String) map.get("hashcode"));

        return meal;
    }

}
