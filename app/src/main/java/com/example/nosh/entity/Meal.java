package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;


/**
 * This is the Meal class, each meals belong to a specific MealPlan day
 * Each Meal will have a name and a list of recipes and ingredients that make up that meal
 */
public class Meal implements Hashable {

    // list of ingredients and recipes
    private ArrayList<MealComponent> mealComponents;

    private String hashcode; // id

    public Meal() {
        hashcode = Hashing
                .sha256()
                .hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
        this.mealComponents = new ArrayList<>();
    }

    public Meal(Meal meal) throws CloneNotSupportedException {
        mealComponents = meal.getMealComponents();
        hashcode = meal.getHashcode();
    }

    // Getters and Setters
    public ArrayList<MealComponent> getMealComponents()
            throws CloneNotSupportedException {
        ArrayList<MealComponent> mealComponents = new ArrayList<>();

        for (MealComponent mealComponent :
                this.mealComponents) {
            mealComponents.add((MealComponent) mealComponent.clone());
        }

        return mealComponents;
    }

    public void setMealComponents(ArrayList<MealComponent> mealComponents)
            throws CloneNotSupportedException {
        if (mealComponents != null) {
            this.mealComponents = new ArrayList<>();

            for (MealComponent mealComponent :
                    mealComponents) {
                this.mealComponents.add((MealComponent) mealComponent.clone());
            }
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
}
