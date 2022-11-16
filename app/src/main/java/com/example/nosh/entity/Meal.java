package com.example.nosh.entity;

import androidx.annotation.NonNull;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;


/**
 * This is the Meal class, each meals belong to a specific MealPlan day
 * Each Meal will have a name and a list of recipes and ingredients that make up that meal
 */
public class Meal implements Hashable, Iterable<MealComponent> {

    // list of ingredients and recipes
    private HashMap<String, MealComponent> mealComponents;

    private String hashcode; // id

    public Meal() {
        hashcode = Hashing
                .sha256()
                .hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
        mealComponents = new HashMap<>();
    }

    public Meal(Meal meal) {
        mealComponents = new HashMap<>();

        for (MealComponent mealComponent :
                meal.getMealComponents()) {
            mealComponents.put(mealComponent.getHashcode(), mealComponent);
        }

        hashcode = meal.getHashcode();
    }

    // Getters and Setters
    public ArrayList<MealComponent> getMealComponents() {
        return new ArrayList<>(this.mealComponents.values());
    }

    public void setMealComponents(HashMap<String, MealComponent> mealComponents) {
        if (mealComponents != null) {
            this.mealComponents = new HashMap<>();

            this.mealComponents.putAll(mealComponents);
        }
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        if (hashcode != null) {
            this.hashcode = hashcode;
        }
    }

    @NonNull
    @Override
    public Iterator<MealComponent> iterator() {
        return getMealComponents().iterator();
    }

    @Override
    public void forEach(@NonNull Consumer<? super MealComponent> action) {
        Iterable.super.forEach(action);
    }
}
