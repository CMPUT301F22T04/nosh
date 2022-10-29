package com.example.nosh.controller;

import com.example.nosh.database.controller.DBController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.repository.IngredientRepository;

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
public class IngredientStorageController {

    private final IngredientRepository ingredientRepository;

    public IngredientStorageController(DBController dbController, Observer o) {
        ingredientRepository = new IngredientRepository(dbController);

        ingredientRepository.addObserver(o);
        ingredientRepository.sync();
    }

    public void add(Date bestBeforeDate, int amount, double unit, String name,
                    String description, String category, String location) {

        ingredientRepository.add(bestBeforeDate, unit, amount, category,
                description, location, name);
    }

    public ArrayList<Ingredient> retrieve() {
        return ingredientRepository.retrieve();
    }

    public void update(String hashcode, Date bestBeforeDate, int amount, double unit,
                       String name, String description, String category,
                       String location) {

        ingredientRepository.update(hashcode, bestBeforeDate, unit, amount, category,
                description, location, name);
    }

    public void delete(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }
}
