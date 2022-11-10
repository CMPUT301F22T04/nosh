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
    private final ArrayList<Foodstuff> foodstuffs; // list of ingredients and recipes
    private Boolean isExpanded;

    public Meal(String name){
        super(name);
        this.hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
        this.foodstuffs = new ArrayList<>();
        isExpanded = false;
    }

    // Getters and Setters
    public ArrayList<Foodstuff> getFoodstuffs() {
        return foodstuffs;
    }

    public void addFoodstuff(Foodstuff foodstuff) {
        this.foodstuffs.add(foodstuff);
    }

    public String getHashcode() {
        return hashcode;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }
}
