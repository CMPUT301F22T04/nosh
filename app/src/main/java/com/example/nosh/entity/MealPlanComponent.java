package com.example.nosh.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;


public class MealPlanComponent extends Hashable implements Iterable<Meal> {

    protected HashMap<String, Meal> meals;

    public MealPlanComponent() {
        super();

        meals = new HashMap<>();
    }

    public MealPlanComponent(MealPlanComponent mealPlanComponent) {
        setMeals(mealPlanComponent.getMeals());

        hashcode = mealPlanComponent.getHashcode();
    }

    public void addMeal(Meal meal) {
        assert meal != null;

        meals.put(meal.getHashcode(), new Meal(meal));
    }

    public void updateMeal(Meal meal) {
        assert meal != null;
        meals.replace(meal.getHashcode(), meal);
    }

    public void removeMeal(Meal meal) {
        assert meal != null;
        meals.remove(meal.getHashcode());
    }

    public ArrayList<Meal> getMeals() {
        ArrayList<Meal> meals = new ArrayList<>();

        for (Meal meal :
                this.meals.values()) {
            meals.add(new Meal(meal));
        }

        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        assert meals != null;

        this.meals = new HashMap<>();
        for (Meal meal:
                meals) {
            this.meals.put(meal.getHashcode(), new Meal(meal));
        }
    }

    public void setMeals(HashMap<String, Meal> meals) {
        this.meals = new HashMap<>();

        for (Map.Entry<String, Meal> entry : meals.entrySet()) {
            this.meals.put(entry.getKey(), new Meal(entry.getValue()));
        }
    }

    void scaleMeal(String hashcode, int scaling) {
        Objects.requireNonNull(meals.get(hashcode)).setServings(scaling);
    }

    void scaleMeals(int scaling) {
        for (Meal meal : meals.values()) {
            meal.setServings(scaling);
        }
    }

    public int getSize() {
        return meals.size();
    }

    @NonNull
    @Override
    public Iterator<Meal> iterator() {
        return getMeals().iterator();
    }

    @Override
    public void forEach(@NonNull Consumer<? super Meal> action) {
        Iterable.super.forEach(action);
    }
}
