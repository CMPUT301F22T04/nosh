package com.example.nosh.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;


/**
 * This is the Meal class, each meals belong to a specific MealPlan day
 * Each Meal will have a name and a list of recipes and ingredients that make up that meal
 */
public class Meal extends Hashable implements Iterable<MealComponent> {
    // list of ingredients and recipes
    protected HashMap<String, MealComponent> mealComponents;
    private long servings;
    private String name;

    public Meal() {
        super();

        mealComponents = new HashMap<>();
    }

    public Meal(long servings, String name) {
        this();

        this.servings = servings;
        this.name = name;
    }

    public Meal(Meal meal) {
        this.setMealComponents(meal.getMealComponents());
        this.setServings(meal.getServings());
        this.setName(meal.getName());
        this.setHashcode(meal.getHashcode());
    }

    public void addMealComponent(MealComponent mealComponent) {
        assert mealComponent != null;
        mealComponents.put(mealComponent.getHashcode(), mealComponent);
    }

    public void removeMealComponent(MealComponent mealComponent) {
        assert mealComponent != null;
        mealComponents.remove(mealComponent.getHashcode());
    }

    // Getters and Setters
    public ArrayList<MealComponent> getMealComponents() {
        assert mealComponents != null;
        return new ArrayList<>(mealComponents.values());
    }

    public void setMealComponents(ArrayList<MealComponent> mealComponents) {
        assert mealComponents != null;

        this.mealComponents = new HashMap<>();
        for (MealComponent mealComponent : mealComponents) {
            this.mealComponents.put(mealComponent.getHashcode(), mealComponent);
        }
    }

    public void setMealComponents(Map<String, MealComponent> mealComponents) {
        assert mealComponents != null;
        this.mealComponents = new HashMap<>();
        this.mealComponents.putAll(mealComponents);
    }

    public long getServings() {
        return servings;
    }

    public void setServings(long servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        assert name != null;
        this.name = name;
    }

    @NonNull @Override public Iterator<MealComponent> iterator () {
        return getMealComponents().iterator();
    }

    @Override public void forEach (@NonNull Consumer < ? super MealComponent > action){
        Iterable.super.forEach(action);
    }
}
