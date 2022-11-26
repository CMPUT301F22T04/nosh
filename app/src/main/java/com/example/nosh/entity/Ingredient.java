package com.example.nosh.entity;

import androidx.annotation.NonNull;

import java.util.Date;


/**
 * Generalization ingredient (can be in ingredient storage, recipe, shopping list)
 */
public class Ingredient extends MealComponent {

    private boolean inStorage = false;
    private Date bestBeforeDate = new Date();
    private String unit;
    private long amount;
    private String category = "";
    private String description = "";
    private String location = "";
    private String name = "";

    public Ingredient() {
        super();
    }

    /**
     * This constructor is for creating ingredients in the ingredient storage
     */
    public Ingredient(Date bestBeforeDate, String unit, long amount,
                      String category, String description, String location,
                      String name) {
        this(unit, amount, category, description, name);

        this.setBestBeforeDate(bestBeforeDate);

        this.location = location;
        inStorage = true;
    }

    public Ingredient(String unit, long amount, String category, String description,
                      String name) {
        this();
        this.amount = amount;
        this.unit = unit;
        this.description = description;
        this.category = category;
        this.name = name;
    }

    public Ingredient(Ingredient ingredient) {
        this.setInStorage(ingredient.isInStorage());
        this.setBestBeforeDate(ingredient.getBestBeforeDate());
        this.setUnit(ingredient.getUnit());
        this.setAmount(ingredient.getAmount());
        this.setCategory(ingredient.getCategory());
        this.setDescription(ingredient.getDescription());
        this.setLocation(ingredient.getLocation());
        this.setName(ingredient.getName());
        this.setHashcode(ingredient.getHashcode());
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
        assert bestBeforeDate != null;
        this.bestBeforeDate = (Date) bestBeforeDate.clone();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
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
        assert category != null;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        assert description != null;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        assert location != null;
        this.location = location;
    }

    public void setName(String name) {
        assert name != null;
        this.name = name;

    }

    @Override
    public String getName() {
        return name;
    }
}
