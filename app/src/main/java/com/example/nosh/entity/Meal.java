package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;


/**
 * This is the Meal class, each meals belong to a specific MealPlan day
 * Each Meal will have a name and a list of recipes and ingredients that make up that meal
 */
public class Meal {

    private Boolean isExpanded;
    private String name;
    private final ArrayList<MealComponent> mealComponents; // list of ingredients and recipes

    private final String hashcode; // id

    public Meal(String name){
        this.name = name;

        this.mealComponents = new ArrayList<>();

        isExpanded = false;

        this.hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public Meal(Meal meal) throws CloneNotSupportedException {
        name = meal.getName();
        mealComponents = meal.getMealComponents();
        isExpanded =  meal.getExpanded();
        hashcode = meal.getHashcode();
    }

    // Getters and Setters
    public ArrayList<MealComponent> getMealComponents() throws CloneNotSupportedException {
        ArrayList<MealComponent> mealComponents = new ArrayList<>();

        for (MealComponent mealComponent :
                this.mealComponents) {
            mealComponents.add((MealComponent) mealComponent.clone());
        }

        return mealComponents;
    }

    public void addMealComponent(MealComponent mealComponent) {
        this.mealComponents.add(mealComponent);
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
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
}
