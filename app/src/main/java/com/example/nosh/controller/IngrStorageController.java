package com.example.nosh.controller;

import com.example.nosh.database.DBController;
import com.example.nosh.repository.StoredIngredientRepo;
import com.example.nosh.entity.ingredient.StoredIngredient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;

/**
 * This class acts as messenger between UI layer for Ingredient Storage and 
 * Data / Model layer for Ingredient Storage. It receive and send all the 
 * requests made by the users via trigger events in the UI layers (e.g., click 
 * add button, click delete button, etc.)
 * 
 * This class is not the owner of data for ingredient, which means that it does 
 * not store copies of data for ingredient and have the right to mutate them  (
 * using setter to change attributes).
 */
public class IngrStorageController {

    private final StoredIngredientRepo storedIngredientRepo;

    public IngrStorageController(DBController dbController, Observer o) {
        storedIngredientRepo = new StoredIngredientRepo(dbController);

        storedIngredientRepo.addObserver(o);
        storedIngredientRepo.sync();
    }

    public void add(Date bestBeforeDate, int amount, double unit, String name,
                    String description, String category, String location) {

        StoredIngredient storedIngredient = new StoredIngredient(bestBeforeDate,
                amount, unit, name, description, category, location);

        storedIngredientRepo.add(storedIngredient);
    }

    public ArrayList<StoredIngredient> retrieve() {
        return storedIngredientRepo.retrieve();
    }

    public void delete(StoredIngredient storedIngredient) {
        storedIngredientRepo.delete(storedIngredient);
    }
}
