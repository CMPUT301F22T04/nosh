package com.example.nosh.entity;


public class StoredIngredient extends Ingredient {

    private String location;

    public StoredIngredient(int amount, int unit, String description,
                            String category, String location) {
        super(amount, unit, description, category);

        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }
}
