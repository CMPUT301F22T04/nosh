package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.Date;


/**
 * Generalization ingredient (can be in ingredient storage, recipe, shopping list)
 */
public class Ingredient {

    private Date bestBeforeDate;
    private double unit;
    private int amount;
    private String category;
    private String description;
    private String location;
    private String name;
    private boolean inStorage;

    /**
     * This field is used for Id of a document in the database
     * The hashcode generate by taking the time of which this object creates, 
     * and then hash using sha256. 
     */
    private String hashcode;

    public Ingredient() {

    }

    /**
     * This constructor is for creating ingredients in the ingredient storage
     */
    public Ingredient(Date bestBeforeDate, double unit, int amount,
                      String category, String description, String location,
                      String name) {
        this(amount, unit, category, description, name);
        this.bestBeforeDate = bestBeforeDate;
        this.location = location;
    }

    public Ingredient(int amount, double unit, String category, String description,
                      String name) {
        this.amount = amount;
        this.unit = unit;
        this.name = name;
        this.description = description;
        this.category = category;
        hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public Date getBestBeforeDate() {
        return bestBeforeDate;
    }

    public void setBestBeforeDate(Date bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }

    public double getUnit() {
        return unit;
    }

    void setUnit(double unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
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

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
