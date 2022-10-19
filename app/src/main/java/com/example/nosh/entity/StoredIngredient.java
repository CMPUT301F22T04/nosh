package com.example.nosh.entity;


import java.util.Date;

public class StoredIngredient extends Ingredient {

    private Date bestBeforeDate;
    private String location;

    public StoredIngredient(Date bestBeforeDate, int amount, int unit, String description,
                            String category, String location) {
        super(amount, unit, description, category);
        this.bestBeforeDate = bestBeforeDate;
        this.location = location;
    }

    public Date getBestBeforeDate() {
        return bestBeforeDate;
    }

    public void setBestBeforeDate(Date bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }

    public String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }
}
