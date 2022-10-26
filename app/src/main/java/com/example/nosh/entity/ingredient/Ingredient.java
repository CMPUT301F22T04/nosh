package com.example.nosh.entity.ingredient;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.Date;


/**
 * Generalization ingredient (can be in ingredient storage, recipe, shopping list)
 */
public class Ingredient {

    private int amount;
    private double unit;
    private String name;
    private String description;
    private String category;
    private String hashcode;

    public Ingredient() {

    }

    public Ingredient(int amount, double unit, String name, String description, String category) {
        this.amount = amount;
        this.unit = unit;
        this.name = name;
        this.description = description;
        this.category = category;
        hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds()).toString();
    }

    public int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    public double getUnit() {
        return unit;
    }

    void setUnit(double unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    void setCategory(String category) {
        this.category = category;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
