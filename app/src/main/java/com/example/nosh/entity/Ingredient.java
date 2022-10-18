package com.example.nosh.entity;

import java.util.Observable;
import java.util.Observer;

public class Ingredient extends Observable {

    private int amount;
    private int unit;
    private String description;
    private String category;

    public Ingredient(int amount, int unit, String description, String category) {
        this.amount = amount;
        this.unit = unit;
        this.description = description;
        this.category = category;
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

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }
}
