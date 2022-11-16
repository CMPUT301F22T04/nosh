package com.example.nosh.entity;

import androidx.annotation.NonNull;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;


/**
 * Generalization ingredient (can be in ingredient storage, recipe, shopping list)
 */
public class Ingredient extends MealComponent implements Serializable, Cloneable {

    private boolean inStorage = false;
    private Date bestBeforeDate = new Date();
    private double unit;
    private long amount;
    private String category = "";
    private String description = "";
    private String location = "";
    private String name = "";

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
        hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    /**
     * This constructor is for creating ingredients in the ingredient storage
     */
    public Ingredient(Date bestBeforeDate, double unit, long amount,
                      String category, String description, String location,
                      String name) {
        this(unit, amount, category, description, name);
        this.bestBeforeDate = bestBeforeDate;
        this.location = location;
        inStorage = true;
    }

    public Ingredient(double unit, long amount, String category, String description,
                      String name) {
        this();
        this.amount = amount;
        this.unit = unit;
        this.description = description;
        this.category = category;
        this.name = name;
    }

    public Ingredient(Ingredient ingredient) {
        inStorage = ingredient.isInStorage();
        bestBeforeDate = ingredient.getBestBeforeDate();
        unit = ingredient.getUnit();
        amount = ingredient.getAmount();
        category = ingredient.getCategory();
        description = ingredient.getDescription();
        location = ingredient.getLocation();
        name = ingredient.getName();
        hashcode = ingredient.getHashcode();
    }

    @NonNull
    @Override
    public Object clone() {
        return new Ingredient(this);
    }

    public boolean isInStorage() {
        return inStorage;
    }

    public void setInStorage(boolean inStorage) {
        this.inStorage = inStorage;
    }

    public Date getBestBeforeDate() {
        return (Date) this.bestBeforeDate.clone();
    }

    public void setBestBeforeDate(Date bestBeforeDate) {
        if (bestBeforeDate != null) {
            this.bestBeforeDate = (Date) bestBeforeDate.clone();
        }
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category != null) {
            this.category = category;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location != null) {
            this.location = location;
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

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
