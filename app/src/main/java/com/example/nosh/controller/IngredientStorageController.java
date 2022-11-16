package com.example.nosh.controller;

import com.example.nosh.entity.Ingredient;
import com.example.nosh.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * This class acts as messenger between UI layer for Ingredient Storage and 
 * Data / Model layer for Ingredient Storage. It receive and send all the 
 * requests made by the users via trigger events in the UI layers (e.g., click 
 * add button, click delete button, etc.)
 * 
 * This class is not the owner of data for ingredient, which means that it does 
 * not store copies of data for ingredient and have the right to mutate them  (
 * using setter to change attributes).
 *
 * @author Dekr0
 * @version 1.0
 */
@Singleton
public class IngredientStorageController {

    final IngredientRepository ingredientRepository;

    // Create dependency of IngredientRepository using Dagger
    @Inject
    public IngredientStorageController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Pass all the necessary information to instantiate an instance of
     * ingredient
     */
    public void add(Date bestBeforeDate, long amount, double unit, String name,
                    String description, String category, String location) {

        ingredientRepository.add(bestBeforeDate, unit, amount, category,
                description, location, name);
    }

    /**
     * Ask IngredientRepository to return a list reference of ingredient objects
     */
    public ArrayList<Ingredient> retrieve() {
        return ingredientRepository.retrieve();
    }

    /**
     * Pass all the information to update an instance of ingredient
     */
    public void update(String hashcode, Date bestBeforeDate, long amount, double unit,
                       String name, String description, String category,
                       String location) {

        ingredientRepository.update(hashcode, bestBeforeDate, unit, amount, category,
                description, location, name);
    }

    public void delete(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }

    /**
     * Register the view model (IngredientFragment) as observer of
     * IngredientRepository
     */
    public void addObserver(Observer o) {
        ingredientRepository.addObserver(o);
    }

    public void deleteObserver(Observer o) {
        ingredientRepository.deleteObserver(o);
    }
}
