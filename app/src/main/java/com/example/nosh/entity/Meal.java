package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;

/**
 * This is the Meal class, each meals belong to a specific MealPlan day
 * Each Meal will have a name and a list of recipes and ingredients that make up that meal
 */
public class Meal extends Foodstuff {
    private final String hashcode; // id
    private String name; // name of the meal
    private final ArrayList<Foodstuff> foodstuffs; // list of ingredients and recipes

    public Meal(String name){
        this.hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
        this.name = name;
        this.foodstuffs = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Foodstuff> getFoodstuffs() {
        return foodstuffs;
    }

    public void addFoodstuff(Foodstuff foodstuff) {
        this.foodstuffs.add(foodstuff);
    }

    public String getHashcode() {
        return hashcode;
    }
}
