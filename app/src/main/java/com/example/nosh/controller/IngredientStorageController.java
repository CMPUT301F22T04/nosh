package com.example.nosh.controller;

import com.example.nosh.database.DatabaseAccessController;
import com.example.nosh.entity.StoredIngredient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class IngredientStorageController {

    private final HashMap<Integer, StoredIngredient> storedIngredientHashMap;

    private final DatabaseAccessController ingredientStorageAccess;

    public IngredientStorageController(DatabaseAccessController ingredientStorageAccess) {
        storedIngredientHashMap = new HashMap<>();
        this.ingredientStorageAccess = ingredientStorageAccess;
    }

    public void add(Date bestBeforeDate, int amount, int unit, String name,
                    String description, String category, String location) {
        StoredIngredient newStoredIngredient = new StoredIngredient(bestBeforeDate,
                amount, unit, name, description, category, location);

        ingredientStorageAccess.create(newStoredIngredient);

        storedIngredientHashMap.put(newStoredIngredient.hashCode(), newStoredIngredient);
    }

    public void remove(StoredIngredient storedIngredient) {
        ingredientStorageAccess.remove(storedIngredient);
        storedIngredientHashMap.remove(storedIngredient.hashCode());
    }

    public ArrayList<StoredIngredient> get() {
        return new ArrayList<>(storedIngredientHashMap.values());
    }
}
