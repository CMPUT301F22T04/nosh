package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;


/**
 * Generalization ingredient (can be in ingredient storage, recipe, shopping list)
 */
public class Ingredient extends Foodstuff implements Hashable, Serializable {

    private boolean inStorage = false;
    private Date bestBeforeDate = new Date();
    private double unit;
    private int amount;
    private String category = "";
    private String description = "";
    private String location = "";

    /**
     * This field is used for Id of a document in the database
     * The hashcode generate by taking the time of which this object creates, 
     * and then hash using sha256. 
     */
    private String hashcode;

    /**
     * For creating new Object from Firestore
     */
    public Ingredient() {
        super("NULL");
    }

    /**
     * This constructor is for creating ingredients in the ingredient storage
     */
    public Ingredient(Date bestBeforeDate, double unit, int amount,
                      String category, String description, String location,
                      String name) {
        this(unit, amount, category, description, name);
        this.bestBeforeDate = bestBeforeDate;
        this.location = location;
        inStorage = true;
    }

    public Ingredient(double unit, int amount, String category, String description,
                      String name) {
        super(name);
        this.amount = amount;
        this.unit = unit;
        this.description = description;
        this.category = category;
        hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public boolean isInStorage() {
        return inStorage;
    }

    public void setInStorage(boolean inStorage) {
        this.inStorage = inStorage;
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

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
