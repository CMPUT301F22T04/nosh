package com.example.nosh.entity;

import androidx.annotation.NonNull;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;


public class MealPlanComponent implements Hashable, Iterable<Meal> {

    private HashMap<String, Meal> meals;

    private String hashcode;

    public MealPlanComponent() {
        meals = new HashMap<>();

        hashcode = Hashing
                .sha256()
                .hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public MealPlanComponent(MealPlanComponent mealPlanComponent) {
        setMeals(mealPlanComponent.getMeals());

        hashcode = mealPlanComponent.getHashcode();
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
        this.meals = new HashMap<>();
        for (Meal meal:
                meals) {
            this.meals.put(meal.getHashcode(), new Meal(meal));
        }
    }

    public void setMeals(HashMap<String, Meal> meals) {
        this.meals = new HashMap<>();
        this.meals.putAll(meals);
    }

    public void addMeal(Meal meal) {
        meals.put(meal.getHashcode(), new Meal(meal));
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal.getHashcode());
    }

    @Override
    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
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
