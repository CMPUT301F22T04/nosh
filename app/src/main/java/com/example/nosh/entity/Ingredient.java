package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;


/**
 * Generalization ingredient (can be in ingredient storage, recipe, shopping list)
 */
public class Ingredient extends Observable {

    private int amount;
    private int unit;
    private String name;
    private String description;
    private String category;
    private String hashcode;

    public Ingredient() {

    }

    public Ingredient(int amount, int unit, String name, String description, String category) {
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

    public int getUnit() {
        return unit;
    }

    void setUnit(int unit) {
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

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }
}
