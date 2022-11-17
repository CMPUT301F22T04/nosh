package com.example.nosh.entity;

import androidx.annotation.NonNull;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import org.checkerframework.checker.units.qual.A;

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
    private Integer servings;
    // list of ingredients and recipes
    private ArrayList<MealComponent> mealComponents;
    private int servings;
    private String name;

    private String hashcode; // id

    public Meal() {
        mealComponents = new ArrayList<>();

        hashcode = Hashing
                .sha256()
                .hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public Meal(int servings, String name) {
        this();

        this.servings = servings;
        this.name = name;
    }

    public Meal(Meal meal) {
        mealComponents = new ArrayList<>();

        mealComponents.addAll(meal.getMealComponents());

        servings = meal.getServings();
        name = meal.getName();

        hashcode = meal.getHashcode();
    }

    // Getters and Setters
    public ArrayList<MealComponent> getMealComponents() {

        return new ArrayList<>(mealComponents);
    }

    public void setMealComponents(ArrayList<MealComponent> mealComponents) {
        if (mealComponents != null) {
            this.mealComponents = new ArrayList<>();
            this.mealComponents.addAll(mealComponents);
        }
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode (String hashcode){
        if (hashcode != null) {
            this.hashcode = hashcode;
        }
    }

    @NonNull @Override public Iterator<MealComponent> iterator () {
        return getMealComponents().iterator();
    }

    @Override public void forEach (@NonNull Consumer < ? super MealComponent > action){
        Iterable.super.forEach(action);
    }
}
