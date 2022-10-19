package com.example.nosh.controller;

import com.example.nosh.entity.StoredIngredient;

import java.util.ArrayList;
import java.util.HashMap;


public class IngredientStorageController {

    private final HashMap<Integer, StoredIngredient> storedIngredientHashMap;

    public IngredientStorageController() {
        storedIngredientHashMap = new HashMap<>();
    }

    public void add(StoredIngredient newStoredIngredients) {
        storedIngredientHashMap.put(newStoredIngredients.hashCode(), newStoredIngredients);
    }

    public void remove(StoredIngredient storedIngredient) {
        storedIngredientHashMap.remove(storedIngredient.hashCode());
    }

    public ArrayList<StoredIngredient> get() {
        return new ArrayList<>(storedIngredientHashMap.values());
    }
}
